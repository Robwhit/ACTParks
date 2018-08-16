package actparks.parksapp.ParkDatabaseFiles;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class ParkRepository {
    private Park park;

    public ParkRepository(){
        park = new Park();
    }

    public static String createTable(){
        return "CREATE TABLE " + Park.TABLE  + "("
                + Park.KEY_ParkID  + " PRIMARY KEY, "
                + Park.KEY_ParkName + " TEXT, "
                + Park.KEY_MapId + " TEXT, "
                + Park.KEY_ParkDescriptoin + " TEXT, "
                + Park.KEY_ParkOpeningHours + " TEXT "
                + ")";
    }


    public int insert(Park park) {
        int parkId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Park.KEY_ParkID, park.getParkId());
        values.put(Park.KEY_ParkName, park.getParkName());
        values.put(Park.KEY_MapId, park.getMapId());
        values.put(Park.KEY_ParkDescriptoin, park.getParkDescription());
        values.put(Park.KEY_ParkOpeningHours, park.getParkOpeningHours());

        // Inserting Row
        parkId=(int)db.insert(Park.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();

        return parkId;
    }

    public void delete( ) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Park.TABLE,null,null);
        DatabaseManager.getInstance().closeDatabase();
    }

//    public String printTable(){
////        return "SELECT * FROM " + Park.TABLE + " WHERE type='table';";
////    }


}
