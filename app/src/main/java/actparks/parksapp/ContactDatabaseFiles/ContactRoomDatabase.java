package actparks.parksapp.ContactDatabaseFiles;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Contact.class}, version = 1)
public abstract class ContactRoomDatabase extends RoomDatabase{
    public abstract ContactDao contactDao();

    private static ContactRoomDatabase INSTANCE;

    static ContactRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (ContactRoomDatabase.class){
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ContactRoomDatabase.class, "contact_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ContactDao mDao;

        PopulateDbAsync(ContactRoomDatabase db) {
            mDao = db.contactDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            Contact contact = new Contact(1, "Lucy", "+61426111000");
            mDao.insert(contact);
            contact = new Contact(2, "Zoe", "+61111333000");
            mDao.insert(contact);
            return null;
        }
    }

}
