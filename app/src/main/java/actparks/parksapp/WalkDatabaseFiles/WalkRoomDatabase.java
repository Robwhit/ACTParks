package actparks.parksapp.WalkDatabaseFiles;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

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
                            .build();
                }
            }
        }

        return INSTANCE;
    }


}
