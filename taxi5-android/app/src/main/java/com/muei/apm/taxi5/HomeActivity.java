package com.muei.apm.taxi5;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // ActionBar ab = getSupportActionBar();
        // ab.setDisplayHomeAsUpEnabled(true);
    }

    public void onClickSearch (View view) {
        Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    // Método para mostrar y ocultar el menú
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    // Método para asignar las funciones correspondientes a las opciones
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_view_profile:
                Toast.makeText(this, "Ver perfil", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_view_history:
                Toast.makeText(this, "Ver histórico", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_log_out:
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }


    public void onClickViewProfile(View view) {
        Toast.makeText(this, "Ver perfil", Toast.LENGTH_SHORT).show();
    }
}
