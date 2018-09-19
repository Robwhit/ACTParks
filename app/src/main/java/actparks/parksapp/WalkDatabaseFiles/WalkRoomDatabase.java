package actparks.parksapp.WalkDatabaseFiles;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

// https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#6

@Database(entities = {Walk.class}, version = 1)
public abstract class WalkRoomDatabase extends RoomDatabase{

    public abstract WalkDao walkDao();

    private static WalkRoomDatabase INSTANCE;

    static WalkRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (WalkRoomDatabase.class){
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WalkRoomDatabase.class, "walk_database")
                            // TODO: Line below adds test database, remove on final
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    // TODO: Temporary file to add stuff to database
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final WalkDao mDao;

        PopulateDbAsync(WalkRoomDatabase db) {
            mDao = db.walkDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            Walk id = new Walk(1, "Robs Walk", "Long Walk__,__Hard Difficulty__,__Long Walk__,__Hard Difficulty__,__Long Walk",
                    4, 5.00,"1:05", "this is Robs walk");
            mDao.insert(id);
            id = new Walk(2, "Harrys Walk","Short Walk__,__Easy Difficulty",
                    2, 7.75,"0:42", "this is Harrys walk");
            mDao.insert(id);
            return null;
        }
    }


}
