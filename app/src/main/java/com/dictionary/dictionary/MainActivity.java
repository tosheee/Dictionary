package com.dictionary.dictionary;

import android.content.Intent;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnAddWord(View v){
        startActivity(new Intent(this, AddWordActivity.class));
    }

    public void btnViewListWord(View v){
        startActivity(new Intent(this, DictionaryListWordsActivity.class));
    }

    public void btnEditData(View v){
        try
        {
            DictionaryDB db = new DictionaryDB(this);
            db.open();
            db.updateEntry("1", "Johan Jurrius", "3413413413");
            Toast.makeText(MainActivity.this, "Succesfully updated!!", Toast.LENGTH_SHORT).show();

        }
        catch (SQLException e)
        {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void btnDeleteAllData(View v) {
        try
        {
            DictionaryDB db = new DictionaryDB(this);
            db.open();
            db.deleteAllData();
            db.close();
            Toast.makeText(MainActivity.this, "Succesfully deleted!!", Toast.LENGTH_SHORT).show();
        }
        catch(SQLException e)
        {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void btnDeleteData(View v) {
        try
        {
            DictionaryDB db = new DictionaryDB(this);
            db.open();
            db.deleteEntry("1");
            db.close();
            Toast.makeText(MainActivity.this, "Succesfully deleted!!", Toast.LENGTH_SHORT).show();
        }
        catch(SQLException e)
        {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void btnUpdateDictionary(View v){
        try
        {
            DictionaryDB db = new DictionaryDB(this);
            db.open();
            db.getAndInsertDataFromFireBase();
            Toast.makeText(MainActivity.this, "Succesfully updated!!", Toast.LENGTH_SHORT).show();
        }
        catch (SQLException e)
        {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
