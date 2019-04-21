package com.muei.apm.taxi5.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.muei.apm.taxi5.R;
import com.muei.apm.taxi5.service.RideService;

import java.util.ArrayList;

public class HistoricalActivity extends AppCompatActivity {

    private RideService rideService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }


        ListView lv = findViewById(R.id.lvHistorical);

        ArrayList<Route> routes = new ArrayList<>();

        // Create the Route objects
        Route route1 = new Route("Viaje realizado el 01/01/2018 a las 12:00", "Pza. Pontevedra, 18","Antonio Insua Rivas, 86");
        Route route2 = new Route("Viaje realizado el 02/02/2018 a las 10:00", "Juan Florez, 56","Av. de Alfonso Molina, Pza. de Madrid");
        Route route3 = new Route("Viaje realizado el 03/03/2018 a las 16:00", "Av. de Navarra, 27","Av. di Ferrocarril, 54");
        Route route4 = new Route("Viaje realizado el 04/04/2018 a las 19:00", "Av. de Hércules, 37","Juan Flórez, 116");
        Route route5 = new Route("Viaje realizado el 05/05/2018 a las 15:00", "Pza. Pontevedra, 18","Antonio Insua Rivas, 86");
        Route route6 = new Route("Viaje realizado el 06/06/2018 a las 17:00", "Juan Florez, 56","Av. de Alfonso Molina, Pza. de Madrid");
        Route route7 = new Route("Viaje realizado el 07/07/2018 a las 18:00", "Av. de Navarra, 27","Av. di Ferrocarril, 54");
        Route route8 = new Route("Viaje realizado el 08/08/2018 a las 20:00", "Av. de Hércules, 37","Juan Flórez, 116");
        Route route9 = new Route("Viaje realizado el 09/09/2018 a las 22:00", "Pza. Pontevedra, 18","Antonio Insua Rivas, 86");
        Route route10 = new Route("Viaje realizado el 10/10/2018 a las 06:00", "Juan Florez, 56","Av. de Alfonso Molina, Pza. de Madrid");
        Route route11 = new Route("Viaje realizado el 11/11/2018 a las 09:00", "Av. de Navarra, 27","Av. di Ferrocarril, 54");
        Route route12 = new Route("Viaje realizado el 12/12/2018 a las 14:00", "Av. de Hércules, 37","Juan Flórez, 116");

        routes.add(route1);
        routes.add(route2);
        routes.add(route3);
        routes.add(route4);
        routes.add(route5);
        routes.add(route6);
        routes.add(route7);
        routes.add(route8);
        routes.add(route9);
        routes.add(route10);
        routes.add(route11);
        routes.add(route12);

        RouteListAdapter adapter = new RouteListAdapter(this, routes);

        lv.setAdapter(adapter);

        /*List<Ride> rides = new ArrayList<Ride>();
        rideService = new RideService(getBaseContext());
        rides.addAll(rideService.getHistorialByUserId(1));*/


    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(HistoricalActivity.this, HomeActivity.class);
        startActivityForResult(intent, 0);
        return true;
    }
}
