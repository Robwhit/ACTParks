package actparks.parksapp.WalkDatabaseFiles;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

//https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#4

@Dao
public interface WalkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Walk walk);

    @Query("DELETE FROM walk_table")
    void deleteAll();


    @Query("SELECT * from walk_table ORDER BY name ASC")
    LiveData<List<Walk>> getAllWords();


}
