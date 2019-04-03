package com.muei.apm.taxi5driver;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

public class InsertCostActivity extends AppCompatActivity {

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_cost);

    }
    
    public void onClickConfirmChanges(View view) {
        Toast.makeText(this, "Enviando el coste...", Toast.LENGTH_SHORT).show();
    }

}
