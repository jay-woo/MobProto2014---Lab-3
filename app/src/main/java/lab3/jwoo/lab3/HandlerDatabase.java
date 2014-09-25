package lab3.jwoo.lab3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by chris on 12/23/13.
 */
public class HandlerDatabase {
    //Database Model
    private ModelDatabase model;

    //Database
    private SQLiteDatabase database;

    //All Fields
    private String[] allColumns = {
            ModelDatabase.USERNAME,
            ModelDatabase.MESSAGE,
            ModelDatabase.TIME,
            ModelDatabase.ID,
    };

    //Public Constructor - create connection to Database
    public HandlerDatabase(Context context){
        model = new ModelDatabase(context);
    }

    /**
     * Add
     */
    public void addChatToDatabase(String username, String chat, String time, String id){

        ContentValues values = new ContentValues();
        values.put(ModelDatabase.USERNAME, username);
        values.put(ModelDatabase.MESSAGE, chat);
        values.put(ModelDatabase.TIME, time);
        values.put(ModelDatabase.ID, id);
        database.insert(ModelDatabase.TABLE_NAME, null, values);
    }

    public void editChat(ChatItem myItem) {
        ContentValues values = new ContentValues();
        values.put(ModelDatabase.TIME, myItem.getTime());
        values.put(ModelDatabase.USERNAME, myItem.getUsername());
        values.put(ModelDatabase.MESSAGE, myItem.getChat());
        values.put(ModelDatabase.ID, myItem.getId());
        database.update(ModelDatabase.TABLE_NAME, values, ModelDatabase.ID + " like '%" + myItem.getId() + "%'", null);
    }

    /**
     * Get
     */
    public ArrayList<ChatItem> getAllChats(){
        return sweepCursor(database.query(ModelDatabase.TABLE_NAME, allColumns, null, null, null, null, null));
    }

    /**
     * Delete
     */

    public void deleteChatById(String id){
        database.delete(
                ModelDatabase.TABLE_NAME,
                ModelDatabase.ID + " like '%" + id + "%'",
                null
        );
    }

    /**Model
     * Additional Helpers
     */
    //Sweep Through Cursor and return a List of Kitties
    private ArrayList<ChatItem> sweepCursor(Cursor cursor){
        ArrayList<ChatItem> allChatItems = new ArrayList<ChatItem>();

        //Get to the beginning of the cursor
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            //Get the Kitty
            ChatItem myChatItem = new ChatItem(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
            );
            //Add the Kitty
            allChatItems.add(myChatItem);
            //Go on to the next Kitty
            cursor.moveToNext();
        }
        return allChatItems;
    }

    //Get Writable Database - open the database
    public void open(){
        database = model.getWritableDatabase();
    }
}