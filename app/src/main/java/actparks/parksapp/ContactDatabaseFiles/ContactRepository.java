package actparks.parksapp.ContactDatabaseFiles;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class ContactRepository {
    private ContactDao mContactDao;
    private LiveData<List<Contact>> mAllContacts;

    ContactRepository(Application application){
        ContactRoomDatabase db = ContactRoomDatabase.getDatabase(application);
        mContactDao = db.contactDao();
        mAllContacts = mContactDao.getAllContacts();
    }

    LiveData<List<Contact>> searchContactName(String s) {
        mAllContacts = mContactDao.searchContactName(s + "%");
        return mAllContacts;
    }

    LiveData<List<Contact>> getmAllContacts() {
        return mAllContacts;
    }

    public void insert(Contact contact){
        new ContactRepository.insertAsyncTask(mContactDao).execute(contact);
    }

    private static class insertAsyncTask extends AsyncTask<Contact, Void, Void> {

        private ContactDao mAsyncTaskDao;

        insertAsyncTask(ContactDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Contact... params){
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
