package actparks.parksapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class WalksFragment extends android.app.Fragment {

    View myView;
    Button walksButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        myView = inflater.inflate(R.layout.fragment_walks, container, false);

        walksButton = (Button) myView.findViewById(R.id.walks_button);
        walksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WalksActivity.class);
                startActivity(intent);
            }
        });

        return myView;
    }

}
