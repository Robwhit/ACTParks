package actparks.parksapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.RatingBar;
import android.widget.TabHost;
import android.widget.TextView;

import org.lucasr.twowayview.TwoWayView;

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

            String name = walk.mName;
            System.out.println(name);

            title = (TextView) findViewById(R.id.walkActivityNameText);
            title.setText(name);

            String[] tagArray = ArrayHelpers.convertStringToArray(walk.tags);
            ArrayList<String> tagArrayList = new ArrayList<>();
            Collections.addAll(tagArrayList, tagArray);
            ArrayAdapter<String> aItems = new ArrayAdapter<String>(this, R.layout.simple_list_item_walk_tags, tagArrayList);
            GridView lvTest = (GridView) findViewById(R.id.walksGridview);
            if (tagArrayList.size() == 0){
                lvTest.setMinimumHeight(0);
            }


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
