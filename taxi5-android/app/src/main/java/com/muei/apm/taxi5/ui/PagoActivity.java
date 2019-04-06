package com.muei.apm.taxi5.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.muei.apm.taxi5.R;

public class PagoActivity extends AppCompatActivity {

    private String origen;
    private String destino;

    private static final String PAGO_ACTIVITY_TAG = PagoActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago);

        Bundle extras = getIntent().getExtras();
        if (extras == null){
            origen = null;
            destino = null;
        } else {
            origen = extras.getString("ORIGEN");
            destino = extras.getString("DESTINO");
        }

        Toast.makeText(this, origen, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, destino, Toast.LENGTH_SHORT).show();

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }


    public void onClickBtnPay(View view) {
        Toast.makeText(this, getString(R.string.activity_pago_paid), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(PagoActivity.this, HomeActivity.class);
        startActivity(intent);
    }


    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(PagoActivity.this, TrayectoActivity.class);
        startActivityForResult(intent, 0);
        return true;
    }

}




