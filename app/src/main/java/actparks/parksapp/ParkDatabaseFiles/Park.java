package actparks.parksapp.ParkDatabaseFiles;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;


@Entity(tableName = "park_table")

public class Park {

    public Park(@NonNull int id) {
        this.parkId = id;
    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "parkId")
    public int parkId;

    @ColumnInfo(name = "name")
    public String parkName;

    @ColumnInfo(name = "mapId")
    public int mapId;

    @ColumnInfo(name = "description")
    public String parkDescription;

    @ColumnInfo(name = "openingHours")
    public String parkOpeningHours;


    public int getParkId() {
        return parkId;
    }

    public void setParkId(int parkId) {
        this.parkId = parkId;
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) { this.parkName = parkName; }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public String getParkDescription() {
        return parkDescription;
    }

    public void setParkDescription(String parkDescription) { this.parkDescription = parkDescription; }

    public String getParkOpeningHours() {
        return parkOpeningHours;
    }

    public void setParkOpeningHours(String parkOpeningHours) { this.parkOpeningHours = parkOpeningHours; }
}


