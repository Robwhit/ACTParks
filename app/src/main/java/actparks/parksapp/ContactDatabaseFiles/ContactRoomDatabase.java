package actparks.parksapp.ContactDatabaseFiles;

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
    public static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ContactDao mDao;

        PopulateDbAsync(ContactRoomDatabase db) {
            mDao = db.contactDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            Contact contact = new Contact(1, "Lucy","", "+61426111000", "https://wattle.anu.edu.au/");
            mDao.insert(contact);
            contact = new Contact(2, "Zoe","", "+61111333000", "https://www.google.com.au/");
            mDao.insert(contact);

            new Thread(new Runnable(){
                public void run(){
                    //open socket
                    try {
                        String host = "35.197.184.151";
                        int port = 10002;
                        Socket sock = new Socket(host, port);
                        DataInputStream in = new DataInputStream(sock.getInputStream());
                        String msg = in.readUTF();
                        Gson gson = new Gson();

                        // con is the received Contact class
                        Contact[] conList = gson.fromJson(msg, Contact[].class);
                        for (int i = 0; i < conList.length; i++){
                            Contact con = conList[i];
                            mDao.insert(con);

                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            //TODO: Receive the contact list
            return null;
        }

    }



}
