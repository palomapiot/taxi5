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

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        inputOrigen = findViewById(R.id.inputOrigen);
        inputDestino = findViewById(R.id.inputDestino);
        btnRequestTaxi = findViewById(R.id.btnRequestTaxi);

        btnRequestTaxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("".equals(inputOrigen.getText().toString().trim())) {
                    Toast.makeText(SearchActivity.this, getString(R.string.enter_origin_address), Toast.LENGTH_SHORT).show();
                } else if ("".equals(inputDestino.getText().toString().trim())) {
                    Toast.makeText(SearchActivity.this, getString(R.string.enter_destination_address), Toast.LENGTH_SHORT).show();
                } else {
                    final Intent intent = new Intent(SearchActivity.this, TrayectoActivity.class);
                    intent.putExtra("ORIGEN", inputOrigen.getText().toString().trim());
                    intent.putExtra("DESTINO", inputDestino.getText().toString().trim());

                    SearchActivity.this.startActivity(intent);
                }
            }
        });

    }
}
