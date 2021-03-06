package com.muei.apm.taxifive.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.muei.apm.taxifive.R;
import com.muei.apm.taxifive.api.APIService;
import com.muei.apm.taxifive.api.ApiObject;
import com.muei.apm.taxifive.api.ApiUtils;
import com.muei.apm.taxifive.api.LoginObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {


    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mNameView;
    private AutoCompleteTextView mLastNameView;
    private AutoCompleteTextView mEmailView;
    private AutoCompleteTextView mPhoneView;
    private EditText mPasswordView;
    private EditText mPasswordRepeatView;
    private View mProgressView;
    private View mLoginFormView;
    private GoogleSignInAccount acct;

    private final String TAG = RegisterActivity.class.getSimpleName();

    // api
    private APIService mAPIService;
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Set up the login form.
        mNameView = findViewById(R.id.name);
        mLastNameView = findViewById(R.id.lastname);
        mEmailView = findViewById(R.id.email);
        mPhoneView = findViewById(R.id.phone);

        mPasswordView = findViewById(R.id.password);
        mPasswordRepeatView = findViewById(R.id.passwordVerified);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        acct = GoogleSignIn.getLastSignedInAccount(this);


    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mNameView.setError(null);
        mLastNameView.setError(null);
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mPhoneView.setError(null);

        // Store values at the time of the login attempt.
        String name = mNameView.getText().toString();
        String lastname = mLastNameView.getText().toString();
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String passwordRepeated = mPasswordRepeatView.getText().toString();
        String phone = mPhoneView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        //Check for a name not null
        if (TextUtils.isEmpty(name)) {
            mNameView.setError(getString(R.string.error_field_required));
            focusView = mNameView;
            cancel = true;
        }

        //Check for a lastname not null
        if (TextUtils.isEmpty(lastname)) {
            mLastNameView.setError(getString(R.string.error_field_required));
            focusView = mLastNameView;
            cancel = true;
        }

        //Check for a password not null
        if (TextUtils.isEmpty(passwordRepeated)) {
            mPasswordRepeatView.setError(getString(R.string.error_incorrect_password));
            focusView = mPasswordRepeatView;
            cancel = true;
        } else if (!passwordRepeated.equals(password)) { // check the same password
            mPasswordRepeatView.setError(getString(R.string.error_incorrect_password));
            focusView = mPasswordRepeatView;
            cancel = true;
        }

        //Check for a password not null
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_incorrect_password));
            focusView = mPasswordView;
            cancel = true;
        } else if (!passwordRepeated.equals(password)) {// check the same password
            mPasswordView.setError(getString(R.string.error_incorrect_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password, name, lastname, phone);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private final String mFirstName;
        private final String mLastName;
        private final String mPhone;

        UserLoginTask(String email, String password, String firstName, String lastName, String phone) {
            mEmail = email;
            mPassword = password;
            mFirstName = firstName;
            mLastName = lastName;
            mPhone = phone;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            mAPIService = ApiUtils.getAPIService();
            ApiObject body = new ApiObject(mFirstName, mLastName, mEmail, mPhone, mPassword);
            mAPIService.createUser(body).enqueue(new Callback<ApiObject>() {
                @Override
                public void onResponse(Call<ApiObject> call, Response<ApiObject> response) {
                    if (response.isSuccessful()) {
                        Log.i(TAG, "post submitted to API." + response.body().toString());
                        mAPIService.loginUser(new LoginObject(mEmail, mPassword)).enqueue(new Callback<LoginObject>() {
                            @Override
                            public void onResponse(Call<LoginObject> call, Response<LoginObject> response) {
                                if (response.isSuccessful()) {
                                    Log.i(TAG, "login submitted to API." + response.body().toString());
                                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                    editor.putLong("currentUserId", response.body().id);
                                    editor.apply();
                                    SharedPreferences sharedPreferences = getSharedPreferences("LOGINGOOGLE", MODE_PRIVATE);
                                    sharedPreferences.edit().putBoolean("LOGINGOOGLE", false).commit();
                                    Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                } else {
                                    Log.i(TAG, "FAILED TO LOG." + response.body().toString());

                                }
                            }

                            @Override
                            public void onFailure(Call<LoginObject> call, Throwable t) {
                                Log.i(TAG, "Unable to submit post to API.");
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<ApiObject> call, Throwable t) {
                    Log.i(TAG, "Unable to submit post to API.");
                }
            });

            try {
                // Simulate network access.
                Thread.sleep(500);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                // TODO guardar register
                SharedPreferences sharedPreferences = getSharedPreferences("LOGINGOOGLE", MODE_PRIVATE);
                sharedPreferences.edit().putBoolean("LOGINGOOGLE", true).commit();
                RegisterActivity.this.finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

