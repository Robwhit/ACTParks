package actparks.parksapp.Walks;

import android.app.FragmentManager;
import android.content.Intent;

import android.location.Location;

import android.support.annotation.NonNull;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;

import android.widget.TextView;
import android.widget.Toast;

import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineListener;
import com.mapbox.android.core.location.LocationEnginePriority;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.CameraMode;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.RenderMode;

import java.util.List;

import actparks.parksapp.R;
import actparks.parksapp.SectionsPageAdapter;
import actparks.parksapp.WalkDatabaseFiles.Walk;

public class  WalksActivity extends AppCompatActivity implements LocationEngineListener,PermissionsListener {

    TextView title;

    Walk walk;
    int mapOpen;
    MapView mapView;

    private SectionsPageAdapter sectionsPageAdapter;

    private MapboxMap map;
    private PermissionsManager permissionsManager;
    private LocationLayerPlugin locationPlugin;
    private LocationEngine locationEngine;
    private Location originLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // MapBox
        Mapbox.getInstance(this, getString(R.string.access_token));


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_walks);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("Walk")) {
            // Gets walk file
            walk = intent.getParcelableExtra("Walk");
            // TODO: get customer details based on customer id

            // Title
            String name = walk.mName;
            title = (TextView) findViewById(R.id.walkActivityNameText);
            title.setText(name);


            //Maps


            mapView = (MapView) findViewById(R.id.mapWalkView);
            mapView.onCreate(savedInstanceState);
//            mapView.getMapAsync(new OnMapReadyCallback() {
//                @Override
//                public void onMapReady(final MapboxMap mapboxMap) {
//                    map = mapboxMap;
//                    enableLocationPlugin();
//                    Log.d("aaa", "aaa");
//
//                    // Customize map with markers, polylines, etc.
//
//                }
//            });

        } else {
            // ...
        }

        // Show Info

        FragmentManager fragmentManager = getFragmentManager();
        getIntent().putExtra("walk", walk);
        fragmentManager.beginTransaction()
                .replace(R.id.walks_info_fragment, new WalksInfo())
                .commit();

        mapView.setVisibility(View.GONE);

        //Info Button
        Button Info_Button = (Button) findViewById(R.id.info_walk_button);
        Info_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FrameLayout info = findViewById(R.id.walks_info_fragment);
                info.setVisibility(View.VISIBLE);
                mapView.setVisibility(View.GONE);
            }
        });

        //Maps Button

        //mapOpen = 0;
        Button Maps_Button = (Button) findViewById(R.id.map_walk_button);
        Maps_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (mapOpen == 0){
//                    // Show Maps
//                    mapOpen = 1;
//                }
                FrameLayout info = findViewById(R.id.walks_info_fragment);

                info.setVisibility(View.GONE);
                mapView.setVisibility(View.VISIBLE);
                mapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(final MapboxMap mapboxMap) {
                        map = mapboxMap;
                        enableLocationPlugin();
                        Log.d("aaa", "aaa");

                        // Customize map with markers, polylines, etc.

                    }
                });

            }
        });

    }


    @Override
    protected void onStop() {
        super.onStop();
        if (locationEngine != null) {
            locationEngine.removeLocationUpdates();
        }
        if (locationPlugin != null) {
            locationPlugin.onStop();
        }
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (locationEngine != null) {
            locationEngine.deactivate();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    @SuppressWarnings( {"MissingPermission"})
    protected void onStart() {
        super.onStart();
        if (locationEngine != null) {
            locationEngine.requestLocationUpdates();
        }
        if (locationPlugin != null) {
            locationPlugin.onStart();
        }
        mapView.onStart();
    }



    // Back button
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        //Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            enableLocationPlugin();
        } else {
            finish();
        }
    }

    @Override
    @SuppressWarnings( {"MissingPermission"})
    public void onConnected() {
        locationEngine.requestLocationUpdates();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            originLocation = location;
            setCameraPosition(location);
            locationEngine.removeLocationEngineListener(this);
        }
    }

    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationPlugin() {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            // Create an instance of LOST location engine
            initializeLocationEngine();

            locationPlugin = new LocationLayerPlugin(mapView, map, locationEngine);
            locationPlugin.setRenderMode(RenderMode.COMPASS);
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @SuppressWarnings( {"MissingPermission"})
    private void initializeLocationEngine() {
        LocationEngineProvider locationEngineProvider = new LocationEngineProvider(this);
        locationEngine = locationEngineProvider.obtainBestLocationEngineAvailable();
        locationEngine.setPriority(LocationEnginePriority.HIGH_ACCURACY);
        locationEngine.activate();

        Location lastLocation = locationEngine.getLastLocation();
        if (lastLocation != null) {
            originLocation = lastLocation;
            setCameraPosition(lastLocation);
        } else {
            locationEngine.addLocationEngineListener((LocationEngineListener) this);
        }
    }

    private void setCameraPosition(Location location) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(location.getLatitude(), location.getLongitude()), 13));
    }
}
