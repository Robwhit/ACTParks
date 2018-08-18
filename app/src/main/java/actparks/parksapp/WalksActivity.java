package actparks.parksapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TabHost;
import android.widget.TextView;

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
        if (intent != null && intent.hasExtra("Id")) {
            Integer name = intent.getIntExtra("Id", 10);
            System.out.println(name);

            title = (TextView) findViewById(R.id.walkActivityNameText);
            title.setText(Integer.toString(name));
            // TODO: get customer details based on customer id
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
