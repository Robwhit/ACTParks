package actparks.parksapp.ParkDatabaseFiles;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class ParkViewModel extends AndroidViewModel {

    private ParkRepository mRepository;

    private LiveData<List<Park>> mAllParks;


    public ParkViewModel(Application application) {
        super(application);
        mRepository = new ParkRepository(application);
        mAllParks = mRepository.getmAllParks();
    }

    //search
    public LiveData<List<Park>> searchParkName(String s) {
        mAllParks = mRepository.searchParkName(s);
        return mAllParks;
    }


    public LiveData<List<Park>> getmAllParks() {
        mAllParks = mRepository.getmAllParks();
        return mAllParks;
    }

    public LiveData<List<Park>> getParksID() {
        mAllParks = mRepository.getParksId();
        return mAllParks;
    }

    public void insert(Park park) {mRepository.insert(park);}
}
