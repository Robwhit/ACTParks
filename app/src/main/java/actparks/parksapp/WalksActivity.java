package actparks.parksapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TabHost;
import android.widget.TextView;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;


import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import actparks.parksapp.Helpers.ArrayHelpers;
import actparks.parksapp.WalkDatabaseFiles.Walk;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class  WalksActivity extends AppCompatActivity {

    TextView title;
    ImageView imageView;
    private MapView mapView;

    private SectionsPageAdapter sectionsPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mapbox.getInstance(this, getString(R.string.access_token));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_walks);

        // Create Tabs
        TabHost host = (TabHost) findViewById(R.id.tabHostParks);
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
        spec.setContent(new TabHost.TabContentFactory() {
            @Override
            public View createTabContent(String s) {
                return mapView;
            }
        });
        host.addTab(spec);



        // Contents


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("Walk")) {
            // Gets walk file
            Walk walk = intent.getParcelableExtra("Walk");
            // TODO: get customer details based on customer id

            // Title
            String name = walk.mName;
            title = (TextView) findViewById(R.id.walkActivityNameText);
            title.setText(name);

            // Tags
            String[] tagArray = ArrayHelpers.convertStringToArray(walk.tags);
            ArrayAdapter<String> aItems = new ArrayAdapter<String>(this, R.layout.simple_list_item_walk_tags, tagArray);
            GridView lvTest = (GridView) findViewById(R.id.walksGridview);
            if (tagArray.length == 0) {
                lvTest.setMinimumHeight(0);
            }

            // Difficulty
            RatingBar difficulty = (RatingBar) findViewById(R.id.ratingWalk);
            difficulty.setRating(walk.mDifficulty);

            // Distance
            TextView distance = (TextView) findViewById(R.id.textWalkDistance);
            distance.setText(walk.mDistance + "km");

            // Time
            TextView time = (TextView) findViewById(R.id.textWalkTime);
            time.setText(walk.mLengthTime + "hrs");

            // Description
            TextView description = (TextView) findViewById(R.id.walkActivityDescriptionText);
            description.setText(walk.mDescription);

            lvTest.setAdapter(aItems);

            // MapBox

            mapView = (MapView) findViewById(R.id.mapWalkView);
            mapView.onCreate(savedInstanceState);
            mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(MapboxMap mapboxMap) {

                // Customize map with markers, polylines, etc.

                }
            });

        } else {
            // ...
        }

    }


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

    // Back button
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }


}
