package actparks.parksapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TabHost;
import android.widget.TextView;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import actparks.parksapp.Helpers.ArrayHelpers;
import actparks.parksapp.WalkDatabaseFiles.Walk;

public class WalksActivity extends AppCompatActivity {

    TextView title;
    ImageView imageView;
    private String STATIC_MAP_API_ENDPOINT = "https://maps.googleapis.com/maps/api/staticmap?autoscale=2&size=640x640&maptype=roadmap&key=AIzaSyB2txl4s1yCTel2L91gkBAQgERRZFcHGvQ&format=png&visual_refresh=true&markers=size:small%7Ccolor:0x1231ff%7Clabel:1%7C-35.27528435,149.12052184576413&markers=size:small%7Ccolor:0x1925ff%7Clabel:1%7C-35.27804185,149.12042495";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_walks);

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
            if (tagArray.length == 0){
                lvTest.setMinimumHeight(0);
            }

            // Difficulty
            RatingBar difficulty = (RatingBar) findViewById(R.id.ratingWalk);
            difficulty.setRating(walk.mDifficulty);

            // Distance
            TextView distance = (TextView) findViewById(R.id.textWalkDistance);
            distance.setText(walk.mDistance +"km");

            // Time
            TextView time = (TextView) findViewById(R.id.textWalkTime);
            time.setText(walk.mLengthTime +"hrs");


            lvTest.setAdapter(aItems);
        } else {
            // ...
        }

        //reference https://www.journaldev.com/10392/google-static-maps-android

        imageView = (ImageView)findViewById(R.id.walkImageView);

            AsyncTask<Void, Void, Bitmap> setImageFromUrl = new AsyncTask<Void, Void, Bitmap>(){
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
                    if (bmp!=null) {

                        imageView.setImageBitmap(bmp);

                    }

                }
            };

            setImageFromUrl.execute();





    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }


}
