package actparks.parksapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ParksFragment extends android.app.Fragment {

    View myView;
    Button parksButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        myView = inflater.inflate(R.layout.fragment_parks, container, false);
        parksButton = (Button) myView.findViewById(R.id.parks_button);
        parksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ParkActivity.class);
                startActivity(intent);
            }
        });
        return myView;

    }

}
