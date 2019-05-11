package com.muei.apm.taxi5driver.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.muei.apm.taxi5driver.R;
import com.muei.apm.taxi5driver.TravelFragment;
import com.muei.apm.taxi5driver.model.Travel;


public class TravelsActiveActivity extends AppCompatActivity implements TravelFragment.OnListFragmentInteractionListener {


    public static final String MY_PREFS_NAME = "MyPrefsFile";

    private SharedPreferences.Editor mEditor;

    //private  mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travels_active);


        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

    }


    @Override
    public void onListFragmentInteraction(Travel item) {
        Intent intent = new Intent(TravelsActiveActivity.this, AcceptPassengerActivity.class);
        intent.putExtra("rideId", item.id);
        startActivity(intent);
    }

    public void onReloadClick(View view) {


    }


    public void onBackPressed() {

    }

    // Método para mostrar y ocultar el menú
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    // Método para asignar las funciones correspondientes a las opciones
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_log_out:
//                mAuth.signOut();
                Intent intentLogout = new Intent(TravelsActiveActivity.this, MainActivity.class);
                final SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                mEditor = prefs.edit();
                mEditor.clear();
                mEditor = getSharedPreferences("LOGINGOOGLE", MODE_PRIVATE).edit();
                mEditor.clear();
                startActivity(intentLogout);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

}
