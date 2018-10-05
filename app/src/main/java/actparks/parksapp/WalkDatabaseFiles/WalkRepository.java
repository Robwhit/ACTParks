package actparks.parksapp.WalkDatabaseFiles;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.location.Location;
import android.os.AsyncTask;

import java.util.List;

import actparks.parksapp.RouteDatabaseFiles.Route;
import actparks.parksapp.RouteDatabaseFiles.RouteDao;

// https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#7

public class WalkRepository {

    private WalkDao mWalkDao;
    private RouteDao mRouteDao;
    private LiveData<List<Walk>> mAllWalks;

    WalkRepository(Application application){
        WalkRoomDatabase db = WalkRoomDatabase.getDatabase(application);
        mWalkDao = db.walkDao();
        mAllWalks = mWalkDao.getAllWalks();
    }

    //search
    LiveData<List<Walk>> searchWalkName(String s) {
        mAllWalks = mWalkDao.searchWalkName(s+"%");
        return  mAllWalks; }

    // sort
    LiveData<List<Walk>> getmAllWalks() {
        mAllWalks = mWalkDao.getAllWalks();
        return mAllWalks;
    }

    LiveData<List<Walk>> sortmDistanct() {
        mAllWalks = mWalkDao.sortWalkDistance();
        return  mAllWalks; }

    LiveData<List<Walk>> sortByDistanceFromMe(Location currentLocation){
        List<Walk> ws = mAllWalks.getValue();
        for (Walk w: ws){
            System.out.println("walk id is: "+w.mId);
            try {
                Route mRoute = mRouteDao.getStartPos( w.mId );

                System.out.println("route x and y: "+mRoute.x + " : " + mRoute.y);

            }
            catch(Exception e) {
                System.out.println("walk id: " + w.mId + " Null Pointer ");
            }

        }
        return  mAllWalks;
    }

    //filter
    LiveData<List<Walk>> filtermByDistance(Float minDistance, Float maxDistance) {
        mAllWalks = mWalkDao.filterWalkDistance(minDistance, maxDistance);
        return  mAllWalks; }

    public void insert(Walk walk){
        new insertAsyncTask(mWalkDao).execute(walk);
    }

    private static class insertAsyncTask extends AsyncTask<Walk, Void, Void> {

        private WalkDao mAsyncTaskDao;

        insertAsyncTask(WalkDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Walk... params){
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

}
