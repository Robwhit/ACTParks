package actparks.parksapp.WalkDatabaseFiles;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.location.Location;
import android.os.AsyncTask;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;
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


//        class WalkDistacnePair implements Comparable<WalkDistacnePair>{
//            Walk walk;
//            double distance;
//            WalkDistacnePair(Walk walk, double distance){
//                this.walk = walk;
//                this.distance = distance;
//            }
//
//            @Override
//            public int compareTo(@NonNull WalkDistacnePair walkDistacnePair) {
//                double i = this.distance - walkDistacnePair.distance;
//                return (int) i;
//
//            }
//        }

    LiveData<List<Walk>> sortByDistanceFromMe(Location currentLocation){
        List<Walk> ws = mAllWalks.getValue();
//        ArrayList<WalkDistacnePair> wdList = new ArrayList();
        for (Walk w: ws){
            System.out.println("walk id is: "+w.mId);
            try {
                ArrayList<LatLng> points = w.routeToArrayList();
                LatLng point = points.get(0);
                double Lan = Math.abs(point.getLatitude());
                double Lon = Math.abs(point.getLongitude());
                double current_Lan = Math.abs(currentLocation.getLatitude());
                double current_Lon = Math.abs(currentLocation.getLongitude());

                //calculate distance between w and currentLocation (we don't need to sqrt the distance)
                double  distance = ((Lan-current_Lan)*(Lan-current_Lan)+(Lon-current_Lon)*(Lon-current_Lon));

//                WalkDistacnePair wd = new WalkDistacnePair(w, distance);
//                wdList.add( wd );
//                System.out.println("-----");
//                System.out.println(wdList.get( 0 ).walk.mName.toString()+wdList.get( 0 ).distance);
                System.out.println("route x and y: "+Lan + " : " + Lon);
                System.out.println("currentLocation x and y: "+current_Lan + " : " + current_Lon);

            }
            catch(Exception e) {
                System.out.println("walk id: " + w.mId + " Null Pointer ");
            }

        }

        //sort by currentLocation, x, y distance
//        Collections.sort( wdList );

//        List<Walk> walkList = new ArrayList<>(  );
//        for (WalkDistacnePair wdp : wdList){
//            walkList.add( wdp.walk );
//            System.out.println("111000111----------");
//            System.out.println(wdp.walk.mName + wdp.distance);
//        }
//        TODO: transfer walkList to LiveData then return
//        LiveData<List<Walk>> result = (LiveData<List<Walk>>) walkList;

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
