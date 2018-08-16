package actparks.parksapp.ParkDatabaseFiles;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import actparks.parksapp.app;

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "sqliteDBPark.db";

    public DatabaseHelper() {
        super(app.getContext(), DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        System.out.println("create a database");
        sqLiteDatabase.execSQL(ParkRepository.createTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        System.out.println("update a database");

    }
}
