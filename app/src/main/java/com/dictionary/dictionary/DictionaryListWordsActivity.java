package com.dictionary.dictionary;

import android.content.DialogInterface;
import android.database.SQLException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DictionaryListWordsActivity extends AppCompatActivity {

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    Map<String, List<String>> expandableListDetail;

    float historicX = Float.NaN, historicY = Float.NaN;
    static final int DELTA = 50;
    enum Direction {LEFT, RIGHT;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_list_words);

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        try
        {
            DictionaryDB db = new DictionaryDB(this);
            db.open();
            expandableListDetail = db.getDataDictionary();
            db.close();
        }
        catch(SQLException e)
        {
            Toast.makeText(DictionaryListWordsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new DictionaryExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);


        SwipeDismissListViewTouchListener touchListener = new SwipeDismissListViewTouchListener(expandableListView, new SwipeDismissListViewTouchListener.DismissCallbacks() {
            @Override
            public boolean canDismiss(int position) {
                return true;
            }

            @Override
            public void onDismiss(ListView view, int[] reverseSortedPositions) {
                Set<String> keySet = expandableListDetail.keySet();

                ArrayList<String> listOfKeys = new ArrayList<String>(keySet);
                for (int position : reverseSortedPositions) {

                //for(String key : expandableListDetail.keySet()){
                  Log.d("ALABALA", "TEST "+ expandableListDetail.get(listOfKeys.get(position)));
                    //expandableListDetail.get(listOfKeys.get(position)).remove();

                //notifyDataSetChanged();
                }
            }
        });

        expandableListView.setOnTouchListener(touchListener);


        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(), expandableListTitle.get(groupPosition) + "", Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition)
            {
                Toast.makeText(getApplicationContext(), expandableListTitle.get(groupPosition) + "", Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(getApplicationContext(), expandableListTitle.get(groupPosition) + " -> " + expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }
}
