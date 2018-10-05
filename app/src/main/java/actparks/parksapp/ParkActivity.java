package actparks.parksapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;

import actparks.parksapp.ParkDatabaseFiles.Park;

public class ParkActivity extends AppCompatActivity {
    TextView title;
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_park);

        // Create Tabs
        TabHost host = (TabHost)findViewById(R.id.tabHostParks);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Information");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Information");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Map");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Map");
        host.addTab(spec);

        //map
        Mapbox.getInstance(this, getString(R.string.access_token));

        //bushwalks button
        final Button button = findViewById(R.id.bushwalks_button_id);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
            }
        });

        // Contents
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("Park")) {
            // Gets park file
            Park park = intent.getParcelableExtra("Park");
            // TODO: get customer details based on customer id

            String name = park.parkName;
            title = (TextView) findViewById(R.id.parksActivityNameText);
            title.setText(name);

            //map
            mapView = findViewById(R.id.mapParkView);
            mapView.onCreate(savedInstanceState);
        } else {
            // ...
        }

    }

    //map
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    //
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
