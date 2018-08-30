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

public class WalksActivity extends AppCompatActivity {

    TextView title;
    ImageView imageView;
    private String STATIC_MAP_API_ENDPOINT = "https://maps.googleapis.com/maps/api/staticmap?center=&zoom=0&scale=2&size=640x640&maptype=roadmap&key=AIzaSyB2txl4s1yCTel2L91gkBAQgERRZFcHGvQ&format=png&visual_refresh=true";

    private int ACCESS_FINE_LOCATION = 1;

    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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
        host.addTab(spec);


        // Difficulty Bar
        RatingBar ratingWalk = findViewById(R.id.ratingWalk);
        ratingWalk.setRating(2);

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
        } else {
            // ...
        }

        //get GPS location

        //reference: https://www.youtube.com/watch?v=kz4wigGXilI
        //reference: https://www.journaldev.com/10392/google-static-maps-android

        imageView = (ImageView) findViewById(R.id.walkImageView);

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                String latitude = Double.toString(location.getLatitude());
                String longitude = Double.toString(location.getLongitude());

                try {
                    String marker1 = "color:orange|label:1|";
                    marker1 = URLEncoder.encode(marker1, "UTF-8");
                    marker1 = marker1 + latitude+","+longitude;

                    Log.d("the current latitude", latitude);
                    Log.d("the currnet longitude", longitude);



                    //STATIC_MAP_API_ENDPOINT = STATIC_MAP_API_ENDPOINT + path + "&markers=" + marker_me + "&markers=" + marker_dest;
                    STATIC_MAP_API_ENDPOINT = STATIC_MAP_API_ENDPOINT + "&markers=" + marker1;

                    AsyncTask<Void, Void, Bitmap> setImageFromUrl = new AsyncTask<Void, Void, Bitmap>() {
                        @Override
                        protected Bitmap doInBackground(Void... params) {
                            Bitmap bmp = null;
                            HttpClient httpclient = new DefaultHttpClient();
                            HttpGet request = new HttpGet(STATIC_MAP_API_ENDPOINT);

                            InputStream in = null;
                            try {
                                HttpResponse response = httpclient.execute(request);
                                in = response.getEntity().getContent();
                                bmp = BitmapFactory.decodeStream(in);
                                in.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return bmp;
                        }

                        protected void onPostExecute(Bitmap bmp) {
                            if (bmp != null) {

                                imageView.setImageBitmap(bmp);

                            }

                        }
                    };

                    setImageFromUrl.execute();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==
                    PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
