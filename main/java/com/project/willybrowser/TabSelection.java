package com.project.willybrowser;
/*
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class TabSelection extends AppCompatActivity {
    static final int max = 100;
    int top1 = -1;
    int top2 = -1;
    int top3 = -1;
    String[] title_data = new String[max];
    String[] url_data = new String[max];
    Bitmap[] icon_data = new Bitmap[max];

    void push_data(String x1, String x2, Bitmap x3) {
        title_data[++top1] = x1;
        url_data[++top2] = x2;
        icon_data[++top3] = x3;
    }

    ImageButton new_tab_btn;
    ImageButton back_btn_tabs;
    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_selection);
        Intent intent = getIntent();
        String UrlTitle = intent.getStringExtra(MainActivity.EXTRA_TEXT);
        String UrlLink = intent.getStringExtra(MainActivity.EXTRA_TEXT2);
        Bitmap UrlLogo = intent.getParcelableExtra(MainActivity.EXTRA_TEXT3);
        push_data(UrlTitle, UrlLink, UrlLogo);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerAdapter = new RecycleAdapter(this, title_data, url_data, icon_data);
        recyclerView.setAdapter(recyclerAdapter);
        new_tab_btn = findViewById(R.id.new_tab_btn);
        back_btn_tabs = findViewById(R.id.back_btn_tabs);

        new_tab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TabSelection.this, MainActivity.class);
                startActivity(i);
            }
        });

        back_btn_tabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}

 */