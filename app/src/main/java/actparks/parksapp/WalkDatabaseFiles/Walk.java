package actparks.parksapp.WalkDatabaseFiles;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.io.IOException;
import java.util.ArrayList;


// https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#3

@Entity(tableName = "walk_table")
public class Walk implements Parcelable{

    public Walk(@NonNull int id, String name, String tags, int difficulty,
                double distance, String lengthTime, String description, String route) {
        this.mId = id;
        this.mName = name;
        this.tags = tags;
        this.mDifficulty = difficulty;
        this.mDistance = distance;
        this.mLengthTime = lengthTime;
        this.mDescription = description;
        this.mRoute = route;
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

    @ColumnInfo(name = "tags")
    public String tags;

    @ColumnInfo(name = "distance")
    public double mDistance;

    @ColumnInfo(name = "dengthTime")
    public String mLengthTime;

    @ColumnInfo(name = "description")
    public String mDescription;

    @ColumnInfo(name = "difficulty")
    public int mDifficulty;

    @ColumnInfo(name = "coordinate")
    public String mCoordinate;

    @ColumnInfo(name = "route")
    public String mRoute;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeString(tags);
        dest.writeInt(mDifficulty);
        dest.writeDouble(mDistance);
        dest.writeString(mLengthTime);
        dest.writeString( mDescription );
        dest.writeString(mRoute);
    }

    public void readFromParcel(Parcel in){
        mId = in.readInt();
        mName = in.readString();
        tags = in.readString();
        mDifficulty = in.readInt();
        mDistance = in.readDouble();
        mLengthTime = in.readString();
        mDescription = in.readString();
        mRoute = in.readString();
    }

    public ArrayList<LatLng> routeToArrayList(){

        ArrayList<LatLng> list = new ArrayList<>();


            String[] split_list = mRoute.split("__,__");

            for (int i = 0; i < split_list.length; i++) {
                String[] sub_str = split_list[i].split(",");
                if (sub_str.length == 3) {
                    LatLng latLng = new LatLng(Double.parseDouble(sub_str[0]), Double.parseDouble(sub_str[1]), Double.parseDouble(sub_str[2]));
                    list.add(latLng);
                }
            }

        return list;

    }
}
