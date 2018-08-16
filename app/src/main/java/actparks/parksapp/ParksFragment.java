package actparks.parksapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;

import java.util.List;

import actparks.parksapp.ParkDatabaseFiles.Park;
import actparks.parksapp.ParkDatabaseFiles.ParkListAdapter;
import actparks.parksapp.ParkDatabaseFiles.ParkViewModel;


public class ParksFragment extends Fragment {

    View myView;
    Button parksButton;
    private ParkViewModel mParkViewModel;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        myView = inflater.inflate(R.layout.fragment_parks, container, false);
        parksButton = (Button) myView.findViewById(R.id.parks_button);
        final Context context = getActivity().getApplicationContext();



        parksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getActivity(), ParkActivity.class);
                //startActivity(intent);
                PopupMenu popupMenu =new PopupMenu(context, v);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.parks_name, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getTitle().equals("park1")) {
                            Intent intent = new Intent(getActivity(), ParkActivity.class);
                            startActivity(intent);
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });



        return myView;

    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView parkrecyclerView = (RecyclerView) myView.findViewById(R.id.parksrecyclerview);
        final ParkListAdapter adapter = new ParkListAdapter(getActivity());
        parkrecyclerView.setAdapter(adapter);
        parkrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        mParkViewModel = ViewModelProviders.of(this).get(ParkViewModel.class);
        mParkViewModel.getmAllParks().observe(this, new Observer<List<Park>>() {
            @Override
            public void onChanged(@Nullable final List<Park> parks) {
                // Update the cached copy of the words in the adapter.
                adapter.setParks(parks);
            }
        });
    }



}
