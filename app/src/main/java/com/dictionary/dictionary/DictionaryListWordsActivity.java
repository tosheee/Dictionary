package com.dictionary.dictionary;

import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class DictionaryListWordsActivity extends AppCompatActivity {

    TextView tvListWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_list_words);

        tvListWords = (TextView) findViewById(R.id.tvListWords);

        try
        {
            DictionaryDB db = new DictionaryDB(this);
            db.open();
            tvListWords.setText(db.getData());
            db.close();
        }
        catch(SQLException e)
        {
            Toast.makeText(DictionaryListWordsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
