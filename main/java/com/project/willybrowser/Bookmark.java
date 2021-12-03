package com.project.willybrowser;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Bookmark extends AppCompatActivity {
    public static final String EXTRA_TEXTS = "com.project.willyBrowser.EXTRA_TEXTS";
    ImageButton back_btn;
    ImageButton delete_bookmark;
    DatabaseHelper2 databaseHelper2;
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmark_page);
        databaseHelper2 = new DatabaseHelper2(this);
        listView = findViewById(R.id.bookmark_listView);
        back_btn = findViewById(R.id.back_btn_bookmark);
        delete_bookmark = findViewById(R.id.delete_bookmark);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        delete_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper2.deleteAllData();
                Toast.makeText(getBaseContext(), "Bookmarks cleared !", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        populateListView();
    }

    private void populateListView() {
        Cursor data = databaseHelper2.getData();
        ArrayList<String> historyList = new ArrayList<>();
        while (data.moveToNext()) {
            historyList.add(data.getString(1));
        }

        final ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historyList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedFromList = (String) listView.getItemAtPosition(i);
                Intent intent = new Intent(Bookmark.this, MainActivity.class);
                intent.putExtra(EXTRA_TEXTS, selectedFromList);
                startActivity(intent);
            }
        });
    }
}
