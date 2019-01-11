package com.dictionary.dictionary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import android.database.SQLException;

public class AddWordActivity extends AppCompatActivity {

    EditText etEnWord, etBgWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        etEnWord = (EditText) findViewById(R.id.etEnWord);
        etBgWord = (EditText) findViewById(R.id.etBgWord);
    }

    public void btnAddWord(View v){

        String enWord = etEnWord.getText().toString().trim();
        String bgWord = etBgWord.getText().toString().trim();

        try
        {
            DictionaryDB db = new DictionaryDB(this);
            db.open();
            db.createEntry(enWord, bgWord);
            db.close();

            Toast.makeText(AddWordActivity.this, "Successfully saved!", Toast.LENGTH_SHORT).show();
            etEnWord.setText("");
            etBgWord.setText("");
        }
        catch(SQLException e)
        {
            Toast.makeText(AddWordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}
