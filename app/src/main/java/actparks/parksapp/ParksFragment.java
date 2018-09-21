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
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.List;

import actparks.parksapp.ParkDatabaseFiles.Park;
import actparks.parksapp.ParkDatabaseFiles.ParkClickListener;
import actparks.parksapp.ParkDatabaseFiles.ParkListAdapter;
import actparks.parksapp.ParkDatabaseFiles.ParkViewModel;


public class ParksFragment extends Fragment {

    View myView;
    Button button_sort;
    private SearchView mySearchView;
    private ParkViewModel mParkViewModel;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        myView = inflater.inflate(R.layout.fragment_parks, container, false);
        final Context context = getActivity().getApplicationContext();

        return myView;

    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView parkrecyclerView = (RecyclerView) myView.findViewById(R.id.parksrecyclerview);
        final ParkListAdapter adapter = new ParkListAdapter(getActivity());
        adapter.setOnItemClickListener(new ParkClickListener() {
            @Override
            public void onParkClick(int position, View v) {
                Intent intent = new Intent(getActivity(), ParkActivity.class);
                Park park = adapter.getPark(position);
//                String name = adapter.getParkName(position);
                intent.putExtra("Park", park);
                startActivity(intent);
            }
        });
        final ParkListAdapter park_adapter = adapter;
        parkrecyclerView.setAdapter(adapter);
        parkrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        mParkViewModel = ViewModelProviders.of(this).get(ParkViewModel.class);
        mParkViewModel.getParksID().observe(this, new Observer<List<Park>>() {
            @Override
            public void onChanged(@Nullable final List<Park> parks) {
                // Update the cached copy of the words in the adapter.
                park_adapter.setParks(parks);
            }
        });

        //search
        //search
        mySearchView = (SearchView) myView.findViewById(R.id.search_bar);

        mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                System.out.println( "TextSubmit : " + s);
                mParkViewModel.searchParkName(s).observe(ParksFragment.this, new Observer<List<Park>>() {
                    @Override
                    public void onChanged(@Nullable final List<Park> parks) {
                        // Update the cached copy of the words in the adapter.
                        park_adapter.setParks(parks);
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                System.out.println( "TextChange : " + s);
                mParkViewModel.searchParkName(s).observe(ParksFragment.this, new Observer<List<Park>>() {
                    @Override
                    public void onChanged(@Nullable final List<Park> parks) {
                        // Update the cached copy of the words in the adapter.
                        park_adapter.setParks(parks);
                    }
                });
                return false;
            }
        });

        //Sort
        button_sort = (Button) myView.findViewById(R.id.parks_sort);
        button_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getContext(), button_sort);
                popup.getMenuInflater()
                        .inflate(R.menu.sort_parks, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(
                                getContext(),
                                "You Clicked : " + item.getTitle(),
                                Toast.LENGTH_SHORT
                        ).show();

                        if (item.getTitle().equals("sort by name")){
                            mParkViewModel.getmAllParks().observe(ParksFragment.this, new Observer<List<Park>>() {
                                @Override
                                public void onChanged(@Nullable final List<Park> parks) {
                                    // Update the cached copy of the words in the adapter.
                                    park_adapter.setParks(parks);
                                }
                            });
                        }

                        return true;
                    }
                });

                popup.show();
            }
        });

        //
    }



}
