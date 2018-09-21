package actparks.parksapp.ContactDatabaseFiles;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Contact contact);

    @Query("DELETE FROM contact_table")
    void deleteAll();

    @Query("SELECT * from contact_table ORDER BY contactId ASC")
    LiveData<List<Contact>> getAllContacts();

    // Search Algorthm
    @Query("SELECT * FROM contact_table WHERE contactName LIKE :search")
    LiveData<List<Contact>> searchContactName(String search);
}
