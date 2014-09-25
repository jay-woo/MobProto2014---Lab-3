package lab3.jwoo.lab3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by wooj on 9/22/14.
 */
public class ModelDatabase extends SQLiteOpenHelper {
    //Table Name
    public static final String TABLE_NAME = "ChatTable";

    //Table Fields
    public static final String USERNAME = "username";
    public static final String MESSAGE = "message";
    public static final String TIME = "time";
    public static final String ID = "id";

    //Database Info
    private static final String DATABASE_NAME = "ChatDatabase";
    private static final int DATABASE_VERSION = 1;

    //Database Creation Statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "("
            + USERNAME + " text not null, "
            + MESSAGE + " text not null, "
            + TIME + " text not null, "
            + ID + " text not null );";

    //Default Constructor
    public ModelDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    //OnCreate Method - creates the ModelDatabase
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);

    }

    @Override
    //OnUpgrade Method - upgrades ModelDatabase if applicable
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(ModelDatabase.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }
}