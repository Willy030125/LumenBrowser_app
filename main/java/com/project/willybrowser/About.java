package com.project.willybrowser;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class About extends AppCompatActivity {
    ImageButton back_btn;
    Button changelog_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_page);

        back_btn = findViewById(R.id.back_btn_about);
        changelog_btn = findViewById(R.id.changelog_btn);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        changelog_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder changelog = new AlertDialog.Builder(About.this);
                changelog.setTitle("Changelog");
                changelog.setMessage("Lumen Browser\n" +
                        "\n" +
                        "v.1.0\n" +
                        "> Initial release as Willy Browser with Webview Engine.\n" +
                        "applicationId: com.project.willybrowser\n" +
                        "minSdkVersion: 17 (Android 4.2 Jellybean)\n" +
                        "targetSdkVersion: 28 (Android 9.0 Pie)\n" +
                        "\n" +
                        "v.1.1\n" +
                        "> Fixed Orientation that made Webview being reverted to homepage again.\n" +
                        "> Fixed unable to watch fullscreen video on Youtube and other sites.\n" +
                        "> Added conditional when user input \"http://\" or \"https://\" on URL Box.\n" +
                        "> URL Box now can do a google search too.\n" +
                        "\n" +
                        "v.1.2\n" +
                        "> App color transformation from Green to Blue themed.\n" +
                        "> Updated app logo with new design as 'Lumen Browser'.\n" +
                        "\n" +
                        "v.1.3\n" +
                        "> UI Renewal matches with prototype's design.\n" +
                        "> Added functional features such as Home button, Forward button, and Reload button.\n" +
                        "> Added ProgressBar when loading a site.\n" +
                        "> Fixed URL Box input that can be entered as multiple line.\n" +
                        "> Fixed Youtube fullscreen video bug (unmatches screen-parent).\n" +
                        "\n" +
                        "v.1.4\n" +
                        "> targetSdkVersion updated to 29 (Android 10).\n" +
                        "> Fixed bottom navigation bar layout on 16:9 phone.\n" +
                        "> URL Box now show current url when finished loading.\n" +
                        "> Added Multi-tabs feature.\n" +
                        "\n" +
                        "v.1.5\n" +
                        "> Added Navigation menu.\n" +
                        "> Added Download manager feature.\n" +
                        "> Redesigned App Logo with greater looking.\n" +
                        "> Added about page for the contributors.\n" +
                        "\n" +
                        "v.1.6\n" +
                        "> SQLite Database integration\n" +
                        "> Added History feature\n" +
                        "> Added Bookmark feature\n" +
                        "> Added Download-history feature\n" +
                        "\n" +
                        "v.1.7\n" +
                        "> Added Image downloader feature when tap and hold on image\n" +
                        "> Added Copy image address option when tap and hold on image\n" +
                        "> Project deployed as an apk (Released)");
                changelog.setPositiveButton("OK", null);
                changelog.setCancelable(true);
                changelog.create().show();
            }
        });
    }
}
