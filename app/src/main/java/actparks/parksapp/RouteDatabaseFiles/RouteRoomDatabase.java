package actparks.parksapp.RouteDatabaseFiles;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Route.class}, version = 1)
public abstract class RouteRoomDatabase extends RoomDatabase {
    public abstract RouteDao routeDao();

    private static RouteRoomDatabase INSTANCE;

    static RouteRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (RouteRoomDatabase.class){
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RouteRoomDatabase.class, "route_database")
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

        private final RouteDao mDao;

        PopulateDbAsync(RouteRoomDatabase db) {
            mDao = db.routeDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            Route walkid = new Route(1, "-35.276435", "149.1250938","20", 1);
            mDao.insert(walkid);
            return null;
        }
    }
}
