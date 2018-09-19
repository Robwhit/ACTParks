package actparks.parksapp.WalkDatabaseFiles;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

// https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#7

public class WalkRepository {

    private WalkDao mWalkDao;
    private LiveData<List<Walk>> mAllWalks;

    WalkRepository(Application application){
        WalkRoomDatabase db = WalkRoomDatabase.getDatabase(application);
        mWalkDao = db.walkDao();
        mAllWalks = mWalkDao.getAllWalks();
    }

    LiveData<List<Walk>> getmAllWalks() {
        mAllWalks = mWalkDao.getAllWalks();
        return mAllWalks;
    }

    LiveData<List<Walk>> sortmDistanct() {
        mAllWalks = mWalkDao.sortWalkDistance();
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
