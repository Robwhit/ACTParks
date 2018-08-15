package actparks.parksapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;


public class WalksFragment extends android.app.Fragment {

    View myView;
    Button walksButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        myView = inflater.inflate(R.layout.fragment_walks, container, false);
        final Context context = getActivity().getApplicationContext();


        // TODO: Remove Button and Replace with List of buttons


        walksButton = (Button) myView.findViewById(R.id.walks_button);
        walksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getActivity(), WalksActivity.class);
                //startActivity(intent);


                PopupMenu popupMenu =new PopupMenu(context, v);
                final MenuInflater menuInflater = popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.bushwalks_name, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getTitle().equals("bushwalk1")) {
                            Intent intent = new Intent(getActivity(), WalksActivity.class);
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

}
