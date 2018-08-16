package actparks.parksapp.ParkDatabaseFiles;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager {
    private Integer myOpenCounter = 0;

    private static DatabaseManager instance;
    private static SQLiteOpenHelper myDatabaseHelper;
    private SQLiteDatabase myDatabase;

    public static synchronized void initializeInstance(SQLiteOpenHelper helper) {
        if (instance == null) {
            instance = new DatabaseManager();
            myDatabaseHelper = helper;
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException(DatabaseManager.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }

        return instance;
    }

    public synchronized SQLiteDatabase openDatabase() {
        myOpenCounter+=1;
        if(myOpenCounter == 1) {
            // Opening new database
            myDatabase = myDatabaseHelper.getWritableDatabase();
        }
        return myDatabase;
    }

    public synchronized void closeDatabase() {
        myOpenCounter-=1;
        if(myOpenCounter == 0) {
            // Closing database
            myDatabase.close();

        }
    }
}
