package actparks.parksapp.RouteDatabaseFiles;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;


public class RouteRepository {
    private RouteDao mRouteDao;
    private LiveData<List<Route>> mAllRoutes;

    RouteRepository(Application application){
        RouteRoomDatabase db = RouteRoomDatabase.getDatabase(application);
        mRouteDao = db.routeDao();
        mAllRoutes = mRouteDao.getAllRoutes();
    }

    LiveData<List<Route>> getAllRoutes() {
        return mAllRoutes;
    }

    public void insert(Route route){
        new RouteRepository.insertAsyncTask(mRouteDao).execute(route);
    }

    private static class insertAsyncTask extends AsyncTask<Route, Void, Void> {

        private RouteDao mAsyncTaskDao;

        insertAsyncTask(RouteDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Route... params){
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

}

