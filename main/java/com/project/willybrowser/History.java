package com.project.willybrowser;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class History extends AppCompatActivity {
    public static final String EXTRA_TXT = "com.project.willyBrowser.EXTRA_TXT";
    ImageButton back_btn;
    ImageButton delete_history;
    DatabaseHelper1 databaseHelper1;
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_page);
        databaseHelper1 = new DatabaseHelper1(this);
        listView = findViewById(R.id.history_listView);
        back_btn = findViewById(R.id.back_btn_history);
        delete_history = findViewById(R.id.delete_history);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        delete_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper1.deleteData();
                Toast.makeText(getBaseContext(), "History cleared !", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        populateListView();
    }

    private void populateListView() {
        Cursor data = databaseHelper1.getData();
        ArrayList<String> historyList = new ArrayList<>();
        while (data.moveToNext()) {
            historyList.add(data.getString(1));
        }

        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historyList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedFromList = (String) listView.getItemAtPosition(i);
                Intent intent = new Intent(History.this, MainActivity.class);
                intent.putExtra(EXTRA_TXT, selectedFromList);
                startActivity(intent);
            }
        });
    }
}
