package actparks.parksapp.ParkDatabaseFiles;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import actparks.parksapp.R;

public class ParkListAdapter extends RecyclerView.Adapter<ParkListAdapter.ParkViewHolder> {
    class ParkViewHolder extends RecyclerView.ViewHolder {
        private final TextView parkItemView;

        private ParkViewHolder(View itemView){
            super(itemView);
            parkItemView = itemView.findViewById(R.id.parks_recycler_text_view);
        }
    }

    private final LayoutInflater mInflater;
    private List<Park> mParks;

    public ParkListAdapter(Context context) {mInflater = LayoutInflater.from(context);}

    @Override
    public ParkViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.parks_recyclerview_item, parent, false);
        return new ParkViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ParkViewHolder holder, int position) {
        if (mParks != null) {
            Park current = mParks.get(position);
            holder.parkItemView.setText(Integer.toString(current.getParkId()));
        } else {
            // Covers the case of data not being ready yet.
            holder.parkItemView.setText("No Park");
        }
    }

    public void setParks(List<Park> parks){
        mParks = parks;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mParks != null)
            return mParks.size();
        else return 0;
    }
}
