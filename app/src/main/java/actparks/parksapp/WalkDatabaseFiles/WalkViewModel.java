package actparks.parksapp.WalkDatabaseFiles;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.location.Location;
import android.support.annotation.NonNull;

import java.security.PublicKey;
import java.util.List;

public class WalkViewModel extends AndroidViewModel{

    private WalkRepository mRepository;

    private LiveData<List<Walk>> mAllWalks;


    public WalkViewModel(Application application) {
        super(application);
        mRepository = new WalkRepository(application);
        mAllWalks = mRepository.getmAllWalks();
    }

    // sort
    public LiveData<List<Walk>> getmAllWalks() {
        mAllWalks = mRepository.getmAllWalks();
        return mAllWalks;
    }

    public LiveData<List<Walk>> sortByDistance() {
        mAllWalks = mRepository.sortmDistanct();
        return mAllWalks;
    }

    public LiveData<List<Walk>> sortByDistanceFromMe(Location currentLocation){
        mAllWalks = mRepository.sortByDistanceFromMe(currentLocation);
        return mAllWalks;
    }

    //search
    public LiveData<List<Walk>> searchWalkName(String s) {
        mAllWalks = mRepository.searchWalkName(s);
        return mAllWalks;
    }



    // filter
    public LiveData<List<Walk>> filterByDistance(Float minDistance, Float maxDistance) {
        mAllWalks = mRepository.filtermByDistance(minDistance, maxDistance);
        return mAllWalks;
    }

    //
    public void insert(Walk walk) {mRepository.insert(walk);}
}
