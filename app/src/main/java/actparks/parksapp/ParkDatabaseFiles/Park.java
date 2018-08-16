package actparks.parksapp.ParkDatabaseFiles;

public class Park {
//    public static final String TAG = Park.class.getSimpleName();
    public static final String TABLE = "Parks";

    // Labels Table Columns names
    public static final String KEY_ParkID = "ParkId";
    public static final String KEY_ParkName = "ParkName";
    public static final String KEY_MapId = "MapId";
    public static final String KEY_ParkDescriptoin= "ParkDescription";
    public static final String KEY_ParkOpeningHours = "ParkOpeningHours";

    public int parkId;
    public String parkName;
    public int mapId;
    public String parkDescription;
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

    public void setParkName(String parkName){
        this.parkName = parkName;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public String getParkDescription() {
        return parkDescription;
    }

    public void setParkDescription(String parkDescription) {
        this.parkDescription = parkDescription;
    }

    public String getParkOpeningHours() {
        return parkOpeningHours;
    }

    public void setParkOpeningHours(String parkOpeningHours) {
        this.parkOpeningHours = parkOpeningHours;
    }


}
