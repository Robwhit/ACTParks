package actparks.parksapp.ContactDatabaseFiles;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class ContactViewModel extends AndroidViewModel{
    private ContactRepository mRepository;

    private LiveData<List<Contact>> mAllContacts;


    public ContactViewModel(Application application) {
        super(application);
        mRepository = new ContactRepository(application);
        mAllContacts = mRepository.getmAllContacts();
    }

    //search
    public LiveData<List<Contact>> searchContactName(String s) {
        mAllContacts = mRepository.searchContactName(s);
        return mAllContacts;
    }


    public LiveData<List<Contact>> getmAllContacts() {
        return mAllContacts;}

    public void insert(Contact contact) {
        mRepository.insert(contact);}
}
