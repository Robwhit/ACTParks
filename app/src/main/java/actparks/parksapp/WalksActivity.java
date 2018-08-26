package actparks.parksapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.RatingBar;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import actparks.parksapp.Helpers.ArrayHelpers;
import actparks.parksapp.WalkDatabaseFiles.Walk;

public class WalksActivity extends AppCompatActivity {

    TextView title;

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



    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}
