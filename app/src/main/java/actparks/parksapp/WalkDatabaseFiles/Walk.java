package actparks.parksapp.WalkDatabaseFiles;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;


// https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#3

@Entity(tableName = "walk_table")
public class Walk implements Parcelable{

    public Walk(@NonNull int id, String name, String description) {
        this.mId = id;
        this.mName = name;
        this.mDescription = description;
    }

    public Walk(Parcel in){
        readFromParcel(in);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Walk createFromParcel(Parcel in ) {
            return new Walk( in );
        }

        public Walk[] newArray(int size) {
            return new Walk[size];
        }
    };

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    public int mId;

    @ColumnInfo(name = "name")
    public String mName;

    @ColumnInfo(name = "description")
    public String mDescription;

    @ColumnInfo(name = "difficulty")
    public int mDifficulty;


    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public int getmDifficulty() {
        return mDifficulty;
    }

    public void setmDifficulty(int mDifficulty) {
        this.mDifficulty = mDifficulty;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeString(mDescription);
    }

    public void readFromParcel(Parcel in){
        mId = in.readInt();
        mName = in.readString();
        mDescription = in.readString();
    }
}
