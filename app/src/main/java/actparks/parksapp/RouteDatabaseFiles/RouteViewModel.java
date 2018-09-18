package actparks.parksapp.RouteDatabaseFiles;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class RouteViewModel extends AndroidViewModel {
    private RouteRepository mRepository;

    private LiveData<List<Route>> mAllRoutes;

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
}
