package actparks.parksapp;

import android.app.Application;
import android.content.Context;

import actparks.parksapp.ParkDatabaseFiles.DatabaseHelper;
import actparks.parksapp.ParkDatabaseFiles.DatabaseManager;


public class app extends Application {
    private static Context context;
    private static DatabaseHelper dbHelper;

    @Override
    public void onCreate()
    {
        super.onCreate();
        context = this.getApplicationContext();
        dbHelper = new DatabaseHelper();
        DatabaseManager.initializeInstance(dbHelper);

    }

    public static Context getContext(){
        return context;
    }

}