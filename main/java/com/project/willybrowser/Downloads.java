package com.project.willybrowser;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Downloads extends AppCompatActivity {
    ImageButton back_btn;
    ImageButton delete_downloads;
    DatabaseHelper3 databaseHelper3;
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_page);
        databaseHelper3 = new DatabaseHelper3(this);
        listView = findViewById(R.id.download_listView);
        back_btn = findViewById(R.id.back_btn_download);
        delete_downloads = findViewById(R.id.delete_downloads);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        delete_downloads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper3.deleteData();
                Toast.makeText(getBaseContext(), "Downloads cleared !", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        populateListView();
    }

    private void populateListView() {
        Cursor data = databaseHelper3.getData();
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
                Uri path = Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/");
                Intent openFile = new Intent(Intent.ACTION_VIEW);
                openFile.setDataAndType(path, "*/*");
                openFile.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                openFile.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                try {
                    startActivity(Intent.createChooser(openFile, "Open with..."));
                }
                catch (Exception e) {
                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
