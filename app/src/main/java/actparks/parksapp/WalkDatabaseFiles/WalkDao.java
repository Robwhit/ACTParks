package actparks.parksapp.WalkDatabaseFiles;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteQuery;
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

    // SORT Algorithms

    // Sort by Name
    @Query("SELECT * from walk_table ORDER BY name ASC")
    LiveData<List<Walk>> getAllWalks();

//    @Query("SELECT * from walk_table ORDER BY name DESC")

    // Sort by Distance
    @Query("SELECT * from walk_table ORDER BY distance ASC")
    LiveData<List<Walk>> sortWalkDistance();

    // FILTER Algorithms

    @Query("SELECT * from walk_table WHERE distance BETWEEN :minDistance AND :maxDistance")
    LiveData<List<Walk>> filterWalkDistance(Float minDistance, Float maxDistance);

    // Search Algorthm
    @Query("SELECT * FROM walk_table WHERE name LIKE :search")
    LiveData<List<Walk>> searchWalkName(String search);


}
