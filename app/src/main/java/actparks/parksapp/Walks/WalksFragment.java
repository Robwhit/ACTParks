package actparks.parksapp.Walks;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import java.util.List;

import actparks.parksapp.MainActivity;
import actparks.parksapp.R;
import actparks.parksapp.RouteDatabaseFiles.Route;
import actparks.parksapp.WalkDatabaseFiles.Walk;
import actparks.parksapp.WalkDatabaseFiles.WalkClickListener;
import actparks.parksapp.WalkDatabaseFiles.WalkListAdapter;
import actparks.parksapp.WalkDatabaseFiles.WalkViewModel;


public class WalksFragment extends Fragment {

    View myView;
    Button walksButton;
    private WalkViewModel mWalkViewModel;
    private SearchView mySearchView;
    Location currentLocation;
    //    WalkViewModel newWalkViewModel;
    Button button_filter;
    Button button_sort;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        myView = inflater.inflate( R.layout.fragment_walks, container, false );
        final Context context = getActivity().getApplicationContext();

        // TODO: Remove Button and Replace with List of buttons


        return myView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
        // Walks Recycler View
        // Recycler View
        RecyclerView walkrecyclerView = (RecyclerView) myView.findViewById( R.id.walksrecyclerview );
        final WalkListAdapter adapter = new WalkListAdapter( getActivity() );
        adapter.setOnItemClickListener( new WalkClickListener() {
            @Override
            public void onWalkClick(int position, View v) {

                Intent intent = new Intent( getActivity(), WalksActivity.class );
                //TODO: Make change position
                Walk walk = adapter.getWalk( position );
                String name = adapter.getWalkName( position );
                intent.putExtra( "Walk", walk );

                startActivity( intent );
            }
        } );
        final WalkListAdapter adapter1 = adapter;
        walkrecyclerView.setAdapter( adapter );
        walkrecyclerView.setLayoutManager( new LinearLayoutManager( getActivity() ) );

        // Walks
        mWalkViewModel = ViewModelProviders.of( this ).get( WalkViewModel.class );

        // initial order
        mWalkViewModel.getmAllWalks().observe( this, new Observer<List<Walk>>() {
            @Override
            public void onChanged(@Nullable final List<Walk> walks) {
                // Update the cached copy of the words in the adapter.
                adapter1.setWalks( walks );
            }
        } );

        //search
        mySearchView = (SearchView) myView.findViewById( R.id.search_bar );

        mySearchView.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                System.out.println( "TextSubmit : " + s );
                mWalkViewModel.searchWalkName( s ).observe( WalksFragment.this, new Observer<List<Walk>>() {
                    @Override
                    public void onChanged(@Nullable final List<Walk> walks) {
                        // Update the cached copy of the words in the adapter.
                        adapter1.setWalks( walks );
                    }
                } );
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                System.out.println( "TextChange : " + s );
                mWalkViewModel.searchWalkName( s ).observe( WalksFragment.this, new Observer<List<Walk>>() {
                    @Override
                    public void onChanged(@Nullable final List<Walk> walks) {
                        // Update the cached copy of the words in the adapter.
                        adapter1.setWalks( walks );
                    }
                } );
                return false;
            }
        } );


        //Filter
        button_filter = (Button) myView.findViewById( R.id.walks_filter_button );
        button_filter.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu( getContext(), button_filter );
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate( R.menu.filter_walks, popup.getMenu() );

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener( new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(
                                getContext(),
                                "You Clicked : " + item.getTitle(),
                                Toast.LENGTH_SHORT
                        ).show();

                        if (item.getTitle().equals( "filter by distance" )) {
                            // alert window to get user's input filter min_distance and max_distance
                            // get xml view
                            LayoutInflater lif = LayoutInflater.from( getContext() );
                            View alertView = lif.inflate( R.layout.filter_alert, null );
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder( getContext() );

                            alertDialogBuilder.setView( alertView );

                            final EditText min_distance = (EditText) alertView
                                    .findViewById( R.id.min_distance );
                            final EditText max_distance = (EditText) alertView
                                    .findViewById( R.id.max_distance );

                            // set dialog message
                            alertDialogBuilder
                                    .setCancelable( false )
                                    .setPositiveButton( "APPLY",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    // check if both min and max are not empty
                                                    if (min_distance.getText().toString().equals( "" ) || max_distance.getText().toString().equals( "" )) {
                                                        Toast.makeText( getContext(), "Input distance Is Empty, Please Enter Some Text", Toast.LENGTH_SHORT ).show();
                                                    } else {
                                                        float min = Float.valueOf( min_distance.getText().toString() );
                                                        float max = Float.valueOf( max_distance.getText().toString() );
                                                        mWalkViewModel.filterByDistance( min, max ).observe( WalksFragment.this, new Observer<List<Walk>>() {
                                                            @Override
                                                            public void onChanged(@Nullable final List<Walk> walks) {
                                                                // Update the cached copy of the words in the adapter.
                                                                adapter1.setWalks( walks );
                                                            }
                                                        } );
                                                    }

                                                }
                                            } )
                                    .setNegativeButton( "Cancel",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            } );

                            // create alert dialog
                            AlertDialog alertDialog = alertDialogBuilder.create();

                            // show it
                            alertDialog.show();


                        } else if (item.getTitle().equals( "filter something later" )) {

                            // TODO: add more filters later
                            // initial order
                            mWalkViewModel.getmAllWalks().observe( WalksFragment.this, new Observer<List<Walk>>() {
                                @Override
                                public void onChanged(@Nullable final List<Walk> walks) {
                                    // Update the cached copy of the words in the adapter.
                                    adapter1.setWalks( walks );
                                }
                            } );

                            Toast.makeText(
                                    getContext(),
                                    "empty now, may filter something later ",
                                    Toast.LENGTH_SHORT
                            ).show();

                        }

                        return true;

                    }
                } );

                popup.show(); //showing popup menu
            }
        } ); //closing the setOnClickListener method


        //Sort
        button_sort = (Button) myView.findViewById( R.id.walks_sort_button );
        button_sort.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu( getContext(), button_sort );
                popup.getMenuInflater()
                        .inflate( R.menu.sort_walks, popup.getMenu() );
                popup.setOnMenuItemClickListener( new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("MissingPermission")
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(
                                getContext(),
                                "You Clicked : " + item.getTitle(),
                                Toast.LENGTH_SHORT
                        ).show();

                        if (item.getTitle().equals( "sort by name" )) {
                            mWalkViewModel.getmAllWalks().observe( WalksFragment.this, new Observer<List<Walk>>() {
                                @Override
                                public void onChanged(@Nullable final List<Walk> walks) {
                                    // Update the cached copy of the words in the adapter.
                                    adapter1.setWalks( walks );
                                }
                            } );
                        } else if (item.getTitle().equals( "sort by distance" )) {

                            mWalkViewModel.sortByDistance().observe( WalksFragment.this, new Observer<List<Walk>>() {
                                @Override
                                public void onChanged(@Nullable final List<Walk> walks) {
                                    // Update the cached copy of the words in the adapter.
                                    adapter1.setWalks( walks );
                                }
                            } );

                        } else if (item.getTitle().equals( "sort by distance from my location" )) {
//                            currentLocation =
//                            System.out.println("");
                            String locationProvider;
                            locationProvider = LocationManager.GPS_PROVIDER;
                            currentLocation = MainActivity.locationManager.getLastKnownLocation( locationProvider );
                            System.out.println("laaa: "+currentLocation.getLatitude()+" -- looo: "+currentLocation.getLongitude());

                            mWalkViewModel.sortByDistance().observe(WalksFragment.this, new Observer<List<Walk>>() {
                                @Override
                                public void onChanged(@Nullable final List<Walk> walks) {
                                    // Update the cached copy of the words in the adapter.
                                    adapter1.setWalks(walks);
                                }
                            });

                        }

                        return true;
                    }
                });

                popup.show();
            }
        });

        //get current location


    }

}
