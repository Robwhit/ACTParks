package actparks.parksapp.ContactDatabaseFiles;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import actparks.parksapp.R;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> {


    class ContactViewHolder extends RecyclerView.ViewHolder{
        private final TextView contactItemView;

        private ContactViewHolder(View itemView){
            super(itemView);
            contactItemView = itemView.findViewById( R.id.contacts_recycler_text_view);
        }

    }


    private final LayoutInflater mInflater;
    private List<Contact> mContacts;

    public ContactListAdapter(Context context) {mInflater = LayoutInflater.from(context);}

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.contacts_recyclerview_item, parent, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        if (mContacts != null) {
            Contact current = mContacts.get(position);
            holder.contactItemView.setText(current.contactName+": "+current.contactNumber);
            //holder.contactItemView.setText(current.contactNumber);
        } else {
            // Covers the case of data not being ready yet.
            holder.contactItemView.setText("No Contact");
        }
    }

    public void setContacts(List<Contact> contacts){
        mContacts = contacts;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mContacts != null)
            return mContacts.size();
        else return 0;
    }

    public String getContactName(int position){
        String contact = mContacts.get(position).contactName;
        return contact;
    }

    public Contact getContact(int position) {
        Contact contact = mContacts.get(position);
        return contact;
    }
}
