package com.muei.apm.taxi5.ui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.muei.apm.taxi5.R;
import com.muei.apm.taxi5.ui.TrayectoActivity;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    public void onClickSearchOrigin(View view) {
        Toast.makeText(this, "Buscar origen", Toast.LENGTH_SHORT).show();
    }

    public void onClickSearchDestination(View view) {
        Toast.makeText(this, "Buscar destino", Toast.LENGTH_SHORT).show();
    }

    public void onClickRequestATaxi(View view) {
        Intent intent = new Intent(SearchActivity.this, TrayectoActivity.class);
        startActivity(intent);
    }
}
