package actparks.parksapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.support.v4.app.Fragment;

import java.util.List;
import java.util.Random;

import actparks.parksapp.WalkDatabaseFiles.Walk;
import actparks.parksapp.WalkDatabaseFiles.WalkClickListener;
import actparks.parksapp.WalkDatabaseFiles.WalkListAdapter;
import actparks.parksapp.WalkDatabaseFiles.WalkViewModel;


public class WalksFragment extends Fragment {

    View myView;
    Button walksButton;
    private WalkViewModel mWalkViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        myView = inflater.inflate(R.layout.fragment_walks, container, false);
        final Context context = getActivity().getApplicationContext();


        // TODO: Remove Button and Replace with List of buttons


        return myView;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Walks Recycler View
        // Recycler View
        RecyclerView walkrecyclerView = (RecyclerView) myView.findViewById(R.id.walksrecyclerview);
        final WalkListAdapter adapter = new WalkListAdapter(getActivity());
        adapter.setOnItemClickListener(new WalkClickListener() {
            @Override
            public void onWalkClick(int position, View v) {

                Intent intent = new Intent(getActivity(), WalksActivity.class);
                //TODO: Make change position
                Walk walk = adapter.getWalk(position);
                intent.putExtra("Walk", walk);
                System.out.println(walk.mId);
                startActivity(intent);
            }
        });
        final WalkListAdapter adapter1 = adapter;
        walkrecyclerView.setAdapter(adapter);
        walkrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Walks
        mWalkViewModel = ViewModelProviders.of(this).get(WalkViewModel.class);
        mWalkViewModel.getmAllWalks().observe(this, new Observer<List<Walk>>() {
            @Override
            public void onChanged(@Nullable final List<Walk> walks) {
                // Update the cached copy of the words in the adapter.
                adapter1.setWalks(walks);
            }
        });

    }



}
