package societyhelp.app.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by divang.sharma on 11/26/2016.
 */
public class InternalStorage extends SQLiteOpenHelper {

    public InternalStorage(Context context)
    {
        super(context, "societhelp.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
