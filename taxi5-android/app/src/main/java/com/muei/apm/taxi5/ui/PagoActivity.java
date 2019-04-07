package com.muei.apm.taxi5.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
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

        TextView precio_label = findViewById(R.id.pago_precio);
        TextView origen_label = findViewById(R.id.pago_origen);
        TextView destino_label = findViewById(R.id.pago_destino);

        precio_label.setText("5 €");
        origen_label.setText(origen);
        destino_label.setText(destino);


        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }


    public void onClickBtnPay(View view) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PagoActivity.this);
        // Configura el titulo.
        alertDialogBuilder.setTitle("Pago");
        // Configura el mensaje.
        alertDialogBuilder
                .setMessage("¿Desea realizar el pago?")
                .setCancelable(false)
                .setPositiveButton("Si",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        Toast.makeText(PagoActivity.this, getString(R.string.activity_pago_paid), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PagoActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                }).create().show();




    }


    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(PagoActivity.this, TrayectoActivity.class);
        startActivityForResult(intent, 0);
        return true;
    }

}




