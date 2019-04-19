package com.muei.apm.taxi5.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.muei.apm.taxi5.R;

public class PaymentActivity extends AppCompatActivity {

    private String origen;
    private String destino;

    private static final String PAGO_ACTIVITY_TAG = PaymentActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            origen = null;
            destino = null;
        } else {
            origen = extras.getString("ORIGEN");
            destino = extras.getString("DESTINO");
        }

        TextView precio_label = findViewById(R.id.pago_precio);
        TextView origen_label = findViewById(R.id.pago_origen);
        TextView destino_label = findViewById(R.id.pago_destino);

        precio_label.setText("6,75€");
        origen_label.setText(origen);
        destino_label.setText(destino);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }


    public void onClickBtnPay(View view) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PaymentActivity.this, R.style.AppCompatAlertDialogStyle);
        // Configura el titulo.
        alertDialogBuilder.setTitle(R.string.payment);
        // Configura el mensaje.
        alertDialogBuilder
                .setMessage(R.string.payment_dialog)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(PaymentActivity.this, getString(R.string.activity_pago_paid), Toast.LENGTH_SHORT).show();

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(PaymentActivity.this, R.style.AppCompatAlertDialogStyle);
                        builder1.setMessage(R.string.agradecimiento);
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Ir a inicio",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent intent = new Intent(PaymentActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();

                        PaymentActivity.this.finish();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).create().show();


    }


    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(PaymentActivity.this, TrayectoActivity.class);
        startActivityForResult(intent, 0);
        return true;
    }

}




