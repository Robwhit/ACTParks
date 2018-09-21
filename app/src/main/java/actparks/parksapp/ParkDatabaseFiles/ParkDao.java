package actparks.parksapp.ParkDatabaseFiles;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ParkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Park park);

    @Query("DELETE FROM park_table")
    void deleteAll();

    @Query("SELECT * from park_table ORDER BY name ASC")
    LiveData<List<Park>> getAllParks();

    @Query("SELECT * from park_table ORDER BY parkId ASC")
    LiveData<List<Park>> getParksId();

}
