package actparks.parksapp.RouteDatabaseFiles;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

public interface RouteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Route route);

    @Query("DELETE FROM route_table")
    void deleteAll();

    @Query("SELECT * from route_table ORDER BY walkid ASC")
    LiveData<List<Route>> getAllRoutes();
}
