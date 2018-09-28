package actparks.parksapp.RouteDatabaseFiles;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;

import java.io.DataInputStream;

import java.io.IOException;
import java.net.Socket;


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
            Route walkid = new Route(1,1, "-35.269279", "149.099063","20", 1);
            mDao.insert(walkid);
            walkid = new Route(2,1, "-35.274439", "149.092291","270", 2);
            mDao.insert(walkid);
            walkid = new Route(3, 1, "-35.277741", "149.098302","110", 3);
            mDao.insert(walkid);

            new Thread(new Runnable(){
                public void run(){
                    //open socket
                    try {
                        String host = "35.197.184.151";
                        int port = 10003;
                        Socket sock = new Socket(host, port);
                        DataInputStream in = new DataInputStream(sock.getInputStream());
                        Gson gson = new Gson();
                        String msg_route = in.readUTF();
                        Route[]route = gson.fromJson(msg_route, Route[].class);

                        for (int i  = 0; i < route.length; i++){
                            Log.d("aaa", route[i].toString());
                            mDao.insert(route[i]);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            return null;
        }
    }
}
