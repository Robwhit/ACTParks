package actparks.parksapp.ParkDatabaseFiles;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;



public class ParkRepository {
    private ParkDao mParkDao;
    private LiveData<List<Park>> mAllParks;

    ParkRepository(Application application){
        ParkRoomDatabase db = ParkRoomDatabase.getDatabase(application);
        mParkDao = db.parkDao();
        mAllParks = mParkDao.getAllParks();
    }

    LiveData<List<Park>> getmAllParks() {
        return mAllParks;
    }

    public void insert(Park park){
        new ParkRepository.insertAsyncTask(mParkDao).execute(park);
    }

    private static class insertAsyncTask extends AsyncTask<Park, Void, Void> {

        private ParkDao mAsyncTaskDao;

        insertAsyncTask(ParkDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Park... params){
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

}
