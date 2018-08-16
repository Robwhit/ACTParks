package actparks.parksapp.ParkDatabaseFiles;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

public abstract class ParkRoomDatabase extends RoomDatabase{
    public abstract ParkDao parkDao();

    private static ParkRoomDatabase INSTANCE;

    static ParkRoomDatabase getDatabase(final Context context){
    if (INSTANCE == null){
        synchronized (ParkRoomDatabase.class){
             if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
             ParkRoomDatabase.class, "park_database")
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

        private final ParkDao mDao;

        PopulateDbAsync(ParkRoomDatabase db) {
            mDao = db.parkDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            Park id = new Park(1);
            mDao.insert(id);
            id = new Park(2);
            mDao.insert(id);
            return null;
        }
    }
}
