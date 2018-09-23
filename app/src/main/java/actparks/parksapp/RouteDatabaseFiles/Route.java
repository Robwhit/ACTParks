package actparks.parksapp.RouteDatabaseFiles;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

//https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#0


@Entity(tableName = "route_table")
public class Route implements Parcelable {

    public Route(@NonNull int id,int walkid, String x, String y, String elevation, int order ){
        this.id = id;
        this.walkid = walkid;
        this.x = x;
        this.y = y;
        this.elevation = elevation;
        this.order = order;

    }

    public Route(Parcel in){
        readFromParcel(in);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Route createFromParcel(Parcel in ) {
            return new Route( in );
        }
        public Route[] newArray(int size) {
            return new Route[size];
        }
    };

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "walkid")
    public int walkid;

    @ColumnInfo(name = "x")
    public String x;

    @ColumnInfo(name = "y")
    public String y;

    @ColumnInfo(name = "elevation")
    public String elevation;

    @ColumnInfo(name = "point_order")
    public int order;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(walkid);
        parcel.writeString(x);
        parcel.writeString(y);
        parcel.writeString(elevation);
        parcel.writeInt(order);

    }

    public void readFromParcel(Parcel in){
        id = in.readInt();
        walkid = in.readInt();
        x = in.readString();
        y = in.readString();
        elevation = in.readString();
        order = in.readInt();
    }


}
