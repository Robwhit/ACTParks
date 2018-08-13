package actparks.parksapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ParkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_park);


    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
