package com.muei.apm.taxi5.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.muei.apm.taxi5.R;

public class SearchActivity extends AppCompatActivity {

    private TextInputEditText inputDestino;
    private TextInputEditText inputOrigen;
    private Button btnRequestTaxi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


        inputOrigen = (TextInputEditText) findViewById(R.id.inputOrigen);
        inputDestino = (TextInputEditText) findViewById(R.id.inputDestino);
        btnRequestTaxi = (Button) findViewById(R.id.btnRequestTaxi);

        btnRequestTaxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("".equals(inputOrigen.getText().toString().trim())) {
                    Toast.makeText(SearchActivity.this, "Introduce dirección de origen", Toast.LENGTH_SHORT).show();
                }
                else if("".equals(inputDestino.getText().toString().trim())) {
                    Toast.makeText(SearchActivity.this, "Introduce dirección de destino", Toast.LENGTH_SHORT).show();
                }
                else {
                    final Intent intent = new Intent(SearchActivity.this, TrayectoActivity.class);
                    intent.putExtra("ORIGEN", inputOrigen.getText().toString().trim());
                    intent.putExtra("DESTINO", inputDestino.getText().toString().trim());

                    SearchActivity.this.startActivity(intent);
                }
            }
        });

    }

    /*public void onClickSearchOrigin(View view) {
        Toast.makeText(this, "Buscar origen", Toast.LENGTH_SHORT).show();
    }

    public void onClickSearchDestination(View view) {
        Toast.makeText(this, "Buscar destino", Toast.LENGTH_SHORT).show();
    }

    public void onClickRequestATaxi(View view) {
        Intent intent = new Intent(SearchActivity.this, TrayectoActivity.class);
        startActivity(intent);
    }*/
}
