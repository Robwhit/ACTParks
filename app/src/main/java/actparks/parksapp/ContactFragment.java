package actparks.parksapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import actparks.parksapp.ContactDatabaseFiles.Contact;
import actparks.parksapp.ContactDatabaseFiles.ContactClickListener;
import actparks.parksapp.ContactDatabaseFiles.ContactListAdapter;
import actparks.parksapp.ContactDatabaseFiles.ContactViewModel;


public class ContactFragment extends Fragment {

    View myView;
    private ContactViewModel mContactViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        myView = inflater.inflate(R.layout.fragment_contact, container, false);
        return myView;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView contactrecyclerView = (RecyclerView) myView.findViewById(R.id.contactsrecyclerview);
        final ContactListAdapter adapter = new ContactListAdapter(getActivity());

        adapter.setOnItemClickListener(new ContactClickListener() {
            @Override
            public void onContactClick(int position, View v) {
                String contactLink = adapter.getContactLink(position);
                System.out.println(contactLink);

                try
                {
                    Intent browserIntent = new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse( contactLink) );
                    startActivity( browserIntent );
                }
                catch (Exception e)
                {
                    e.getMessage();
                }
            }
        });
        final ContactListAdapter contact_adapter = adapter;
        contactrecyclerView.setAdapter(adapter);
        contactrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        mContactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);
        mContactViewModel.getmAllContacts().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(@Nullable final List<Contact> contacts) {
                // Update the cached copy of the words in the adapter.
                contact_adapter.setContacts(contacts);
            }
        });
    }

}
