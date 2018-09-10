package actparks.parksapp.Walks;

import android.Manifest;
import android.app.FragmentManager;
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
import android.widget.Button;
import android.widget.FrameLayout;
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
import actparks.parksapp.R;
import actparks.parksapp.SectionsPageAdapter;
import actparks.parksapp.WalkDatabaseFiles.Walk;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class  WalksActivity extends AppCompatActivity {

    TextView title;
    ImageView imageView;
    private MapView mapView;
    Walk walk;
    int mapOpen;

    private SectionsPageAdapter sectionsPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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





        } else {
            // ...
        }




        // Show Info



        FragmentManager fragmentManager = getFragmentManager();
        getIntent().putExtra("walk", walk);
        fragmentManager.beginTransaction()
                .replace(R.id.walks_info_fragment, new WalksInfo())
                .commit();





        FrameLayout maps = findViewById(R.id.walks_map_fragment);
        maps.setVisibility(View.GONE);
        //Info Button


        Button Info_Button = (Button) findViewById(R.id.info_walk_button);
        Info_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FrameLayout info = findViewById(R.id.walks_info_fragment);
                FrameLayout maps = findViewById(R.id.walks_map_fragment);
                info.setVisibility(View.VISIBLE);
                maps.setVisibility(View.GONE);
            }
        });

        //Maps Button

        mapOpen = 0;
        Button Maps_Button = (Button) findViewById(R.id.map_walk_button);
        Maps_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mapOpen == 0){
                    // Show Maps
                    getIntent().putExtra("walk", walk);
                    fragmentManager.beginTransaction()
                            .replace(R.id.walks_map_fragment, new WalksMap())
                            .commit();
                    mapOpen = 1;
                }
                FrameLayout info = findViewById(R.id.walks_info_fragment);
                FrameLayout maps = findViewById(R.id.walks_map_fragment);
                info.setVisibility(View.GONE);
                maps.setVisibility(View.VISIBLE);
            }
        });
    }



    // Back button
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }


}
