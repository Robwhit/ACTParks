package actparks.parksapp.WalkDatabaseFiles;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import actparks.parksapp.MainActivity;
import actparks.parksapp.R;

import static actparks.parksapp.MainActivity.locationManager;

public class WalkListAdapter extends RecyclerView.Adapter<WalkListAdapter.WalkViewHolder>{
    private static WalkClickListener clickListener;
    Location currentLocation;

    class WalkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView walkTitleView;
        private final TextView walkDistanceView;
        private final TextView distanceFromMyLocation;

        private WalkViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            walkTitleView = itemView.findViewById(R.id.walks_recycler_title_view);
            walkDistanceView = itemView.findViewById(R.id.walks_recycler_distance_view);
            distanceFromMyLocation = itemView.findViewById(R.id.walks_distance_from_my_location);
        }

        @Override
        public void onClick(View v) {
            clickListener.onWalkClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(WalkClickListener clickListener) {
        WalkListAdapter.clickListener = clickListener;
    }

    private final LayoutInflater mInflater;
    private List<Walk> mWalks;

    public WalkListAdapter(Context context) {mInflater = LayoutInflater.from(context);}

    @Override
    public WalkViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.walks_recyclerview_item, parent, false);
        return new WalkViewHolder(itemView);
    }

    @SuppressLint("MissingPermission")
    public String getDistanceFromMyLocatiôn(LatLng point){
        //get my current location
        String locationProvider;
        locationProvider = LocationManager.GPS_PROVIDER;
        currentLocation = MainActivity.locationManager.getLastKnownLocation( locationProvider );
        System.out.println("laaa: "+currentLocation.getLatitude()+" -- looo: "+currentLocation.getLongitude());

        //get the lan and lon
        double Lan = Math.abs(point.getLatitude());
        double Lon = Math.abs(point.getLongitude());
        double current_Lan = Math.abs(currentLocation.getLatitude());
        double current_Lon = Math.abs(currentLocation.getLongitude());

        //calculate distance between w and currentLocation (we don't need to sqrt the distance)
        double  distance = ((Lan-current_Lan)*(Lan-current_Lan)+(Lon-current_Lon)*(Lon-current_Lon));

        //set double precision
        DecimalFormat df = new DecimalFormat( "0.00 ");
        return df.format(distance);
    }


    // TODO: Change buttons on list
    @Override
    public void onBindViewHolder(WalkViewHolder holder, int position) {
        if (mWalks != null) {
            Walk current = mWalks.get(position);

            holder.walkTitleView.setText(current.mName);
            holder.walkDistanceView.setText(current.mDistance+"km");

            //get walk's start point
            Walk walk;
//            if (current.mRoute != null && current.mRoute != ""){
            ArrayList<LatLng> points = current.routeToArrayList();
            if(!points.isEmpty()){
            LatLng point = points.get( 0 );

//            holder.distanceFromMyLocation.setText( getDistanceFromMyLocatiôn(point) + "km from my location" );
            holder.distanceFromMyLocation.setText( getDistanceFromMyLocatiôn(point) + "km far away" );
            } else{
                holder.distanceFromMyLocation.setText("no walk's route yet");
            }
            }
            else {
            // Covers the case of data not being ready yet.
            // Nothing in Data
        }
    }

    public void setWalks(List<Walk> walks){
        mWalks = walks;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mWalks != null)
            return mWalks.size();
        else return 0;
    }

    public int getWalkId(int position) {
        int walk = mWalks.get(position).mId;
        return walk;
    }

    public String getWalkName(int position){
        String walk = mWalks.get(position).mName;
        return walk;
    }

    public Walk getWalk(int position) {
        Walk walk = mWalks.get(position);
        return walk;
    }

}
