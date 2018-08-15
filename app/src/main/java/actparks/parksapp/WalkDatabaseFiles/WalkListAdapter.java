package actparks.parksapp.WalkDatabaseFiles;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import actparks.parksapp.R;

public class WalkListAdapter extends RecyclerView.Adapter<WalkListAdapter.WalkViewHolder>{

    class WalkViewHolder extends RecyclerView.ViewHolder {
        private final TextView walkItemView;

        private WalkViewHolder(View itemView){
            super(itemView);
            walkItemView = itemView.findViewById(R.id.walks_recycler_text_view);
        }
    }

    private final LayoutInflater mInflater;
    private List<Walk> mWalks;

    public WalkListAdapter(Context context) {mInflater = LayoutInflater.from(context);}

    @Override
    public WalkViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.walks_recyclerview_item, parent, false);
        return new WalkViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WalkViewHolder holder, int position) {
        if (mWalks != null) {
            Walk current = mWalks.get(position);
            holder.walkItemView.setText(current.getmId());
        } else {
            // Covers the case of data not being ready yet.
            holder.walkItemView.setText("No Walk");
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
}
