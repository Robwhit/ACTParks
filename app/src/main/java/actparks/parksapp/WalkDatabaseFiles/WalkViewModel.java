package actparks.parksapp.WalkDatabaseFiles;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class WalkViewModel extends AndroidViewModel{

    private WalkRepository mRepository;

    private LiveData<List<Walk>> mAllWalks;


    public WalkViewModel(Application application) {
        super(application);
        mRepository = new WalkRepository(application);
        mAllWalks = mRepository.getmAllWalks();
    }

    public LiveData<List<Walk>> getmAllWalks() { return mAllWalks;}

    public void insert(Walk walk) {mRepository.insert(walk);}
}
