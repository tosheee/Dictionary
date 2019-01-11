package com.dictionary.dictionary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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
}
