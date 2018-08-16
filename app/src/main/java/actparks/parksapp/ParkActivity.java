package actparks.parksapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

public class ParkActivity extends AppCompatActivity {

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


    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
