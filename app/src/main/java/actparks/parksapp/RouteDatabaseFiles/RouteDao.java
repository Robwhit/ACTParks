package actparks.parksapp.RouteDatabaseFiles;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import actparks.parksapp.WalkDatabaseFiles.Walk;

@Dao
public interface RouteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Route route);

    @Query("DELETE FROM route_table")
    void deleteAll();

    @Query("SELECT * from route_table ORDER BY walkid ASC")
    LiveData<List<Route>> getAllRoutes();

    @Query("SELECT * FROM route_table WHERE walkid LIKE :id ORDER BY point_order ASC")
    LiveData<List<Route>> getRouteWithId(int id);

    @Query("SELECT * FROM route_table WHERE walkid LIKE :id AND point_order LIKE 1")
    Route getStartPos(int id);
}
