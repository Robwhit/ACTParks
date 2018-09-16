package actparks.parksapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import actparks.parksapp.ContactDatabaseFiles.Contact;
import actparks.parksapp.Helpers.ServerConnection;


public class HomeFragment extends Fragment {

    // TODO: Adds recommendations

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        myView = inflater.inflate(R.layout.fragment_home, container, false);


        return myView;
    }





}
