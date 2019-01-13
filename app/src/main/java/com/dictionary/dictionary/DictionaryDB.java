package com.dictionary.dictionary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DictionaryDB {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_EN_WORD = "en_word";
    public static final String KEY_BG_WORD = "bg_word";

    private final String DATABASE_NAME = "DictionaryDB";
    private final String DATABASE_TABLE = "DictionaryTable";
    private final int DATABASE_VERSION = 1;

    private DBHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    List<String> result;

    public DictionaryDB(Context context) {
        ourContext = context;
    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sqlCode = "CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_EN_WORD  + " TEXT NOT NULL, " + KEY_BG_WORD  + " TEXT NOT NULL);";
            db.execSQL(sqlCode);
        }
    }

    public DictionaryDB open() throws SQLException {
        ourHelper = new DBHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        ourHelper.close();
    }

    public long createEntry(String enWord, String bgWord) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_EN_WORD, enWord);
        cv.put(KEY_BG_WORD, bgWord);
        return ourDatabase.insert(DATABASE_TABLE, null, cv);
    }


    //
    public void getAndInsertDataFromFireBase(){

        final DatabaseReference senderDb = FirebaseDatabase.getInstance().getReference().child("Items");

        senderDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    //Log.i("Testing", "DATA: "+ childDataSnapshot.child("bg_word").getValue().toString());
                    createEntry(childDataSnapshot.child("en_word").getValue().toString(), childDataSnapshot.child("bg_word").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    public HashMap<String, List<String>> getDataDictionary() {
        String [] columns = new String [] {KEY_ROWID, KEY_EN_WORD, KEY_BG_WORD};
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);

        int iRowID = c.getColumnIndex(KEY_ROWID);
        int iEnWord =  c.getColumnIndex(KEY_EN_WORD);
        int iBgWord =  c.getColumnIndex(KEY_BG_WORD);

        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

            //result = result + c.getString(iRowID) + ": " + c.getString(iEnWord) + " " + c.getString(iBgWord) + "\n";

            List<String> bgWordArray = new ArrayList<String>();

            bgWordArray.add(c.getString(iBgWord));

            expandableListDetail.put(c.getString(iEnWord), bgWordArray);

        }

        c.close();
        return expandableListDetail;
    }

    // method for remove
    public String getData() {
        String [] columns = new String [] {KEY_ROWID, KEY_EN_WORD, KEY_BG_WORD};
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);

        String result = "";

        int iRowID = c.getColumnIndex(KEY_ROWID);
        int iEnWord =  c.getColumnIndex(KEY_EN_WORD);
        int iBgWord =  c.getColumnIndex(KEY_BG_WORD);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            result = result + c.getString(iRowID) + ": " + c.getString(iEnWord) + " " + c.getString(iBgWord) + "\n";
        }

        c.close();
        return result;
    }
}
