package actparks.parksapp.Walks;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.RatingBar;
import android.widget.TextView;

import actparks.parksapp.Helpers.ArrayHelpers;
import actparks.parksapp.R;
import actparks.parksapp.WalkDatabaseFiles.Walk;


public class WalksInfo extends Fragment {

    // TODO: Adds recommendations

    View myView;

    Walk walk;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        myView = inflater.inflate(R.layout.fragment_walksinfo, container, false);
        return myView;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Intent i  = getActivity().getIntent();

        if (i != null && i.hasExtra("Walk")) {

            walk = (Walk) i.getParcelableExtra("walk");
        }


        // Tags
        System.out.println(walk.mName);
        String[] tagArray = ArrayHelpers.convertStringToArray(walk.tags);
        ArrayAdapter<String> aItems = new ArrayAdapter<String>(getActivity(), R.layout.simple_list_item_walk_tags, tagArray);
        GridView lvTest = (GridView) getActivity().findViewById(R.id.walksGridview);
        if (tagArray.length == 0) {
            lvTest.setMinimumHeight(0);
        }

        // Difficulty
        RatingBar difficulty = (RatingBar) getActivity().findViewById(R.id.ratingWalk);
        difficulty.setRating(walk.mDifficulty);

        // Distance
        TextView distance = (TextView) getActivity().findViewById(R.id.textWalkDistance);
        distance.setText(walk.mDistance + "km");

        // Time
        TextView time = (TextView) getActivity().findViewById(R.id.textWalkTime);
        time.setText(walk.mLengthTime + "hrs");

        // Description
        TextView description = (TextView) getActivity().findViewById(R.id.walkActivityDescriptionText);
        description.setText(walk.mDescription);

        lvTest.setAdapter(aItems);




    }

}
