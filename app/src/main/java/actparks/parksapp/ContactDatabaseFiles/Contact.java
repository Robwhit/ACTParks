package actparks.parksapp.ContactDatabaseFiles;

import android.arch.persistence.room.*;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity(tableName = "contact_table")
public class Contact implements Parcelable{

    public Contact(@NonNull int contactId, String contactName, String contactNumber, String contactLink){
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactNumber = contactNumber;
        this.contactLink = contactLink;
    }
    public Contact(Parcel in){
        readFromParcel(in);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Contact createFromParcel(Parcel in ) {
            return new Contact( in );
        }
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "contactId")
    public int contactId;

    @ColumnInfo(name = "contactName")
    public String contactName;

    @ColumnInfo(name = "contactNumber")
    public String contactNumber;

    @ColumnInfo(name = "contactLink")
    public String contactLink;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(contactId);
        dest.writeString(contactName);
        dest.writeString(contactNumber);
        dest.writeString( contactLink );
    }
    public void readFromParcel(Parcel in){
        contactId = in.readInt();
        contactName = in.readString();
        contactNumber = in.readString();
        contactLink = in.readString();
    }
}
