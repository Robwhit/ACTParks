package actparks.parksapp.WalkDatabaseFiles;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


// https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#3

@Entity(tableName = "walk_table")
public class Walk {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    public int mId;

    @NonNull
    @ColumnInfo(name = "name")
    public String mName;

    @NonNull
    @ColumnInfo(name = "description")
    public String mDescription;

    @NonNull
    @ColumnInfo(name = "difficulty")
    public int mDifficulty;


    @NonNull
    public int getmId() {
        return mId;
    }

    public void setmId(@NonNull int mId) {
        this.mId = mId;
    }

    @NonNull
    public String getmName() {
        return mName;
    }

    public void setmName(@NonNull String mName) {
        this.mName = mName;
    }

    @NonNull
    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(@NonNull String mDescription) {
        this.mDescription = mDescription;
    }

    @NonNull
    public int getmDifficulty() {
        return mDifficulty;
    }

    public void setmDifficulty(@NonNull int mDifficulty) {
        this.mDifficulty = mDifficulty;
    }
}
