package actparks.parksapp.RouteDatabaseFiles;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.graphics.Point;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import actparks.parksapp.WalkDatabaseFiles.Walk;

public class RouteViewModel extends AndroidViewModel {
    private RouteRepository mRepository;

    private LiveData<List<Route>> mAllRoutes;
    private LiveData<List<Route>> mRoute;

    public RouteViewModel (Application application) {
        super(application);
        mRepository = new RouteRepository(application);
        mAllRoutes = mRepository.getAllRoutes();
    }

    LiveData<List<Route>> getAllRoutes() {
        return mAllRoutes;
    }



    public void insert(Route route) {
        mRepository.insert(route);
    }


    // TODO: Recieve the route from the database and create ArrayLists from it;
    public LiveData<List<Route>> getRouteWithId(int walkId){
        mRoute = mRepository.getmRouteWithId(walkId);
        return mRoute;
    }
}
