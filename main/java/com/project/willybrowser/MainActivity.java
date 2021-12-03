package com.project.willybrowser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.Manifest;
import android.app.DownloadManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    private long backPressedTime;
    private Toast backToast;

    WebView web_browser;
    ImageButton search_btn;
    ImageButton back_btn;
    ImageButton fav_btn;
    ImageButton forward_btn;
    ImageButton home_btn;
    ImageButton refresh_btn;
    ImageButton tab_btn;
    ImageButton menu_btn;
    EditText url_txt;
    ProgressBar progressBar;
    DrawerLayout nav_menu;
    NavigationView nav_View;
    String SiteTitleParse1;
    String SiteTitleParse2;
    String SiteLinkParse1;
    String SiteLinkParse2;
    Bitmap SiteLogoParse1;
    Bitmap SiteLogoParse2;
    TextView display_siteInfo;
    TextView display_siteUrl;
    ImageView tab_siteImage;
    DatabaseHelper1 databaseHelper1;
    DatabaseHelper2 databaseHelper2;
    DatabaseHelper3 databaseHelper3;
    String DownloadImageUrl;

    public void getSiteInfo(String text1, String text2) {
        SiteTitleParse2 = text1;
        SiteLinkParse2 = text2;
    }

    public void getSiteLogo(Bitmap logo) {
        SiteLogoParse2 = logo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        web_browser = findViewById(R.id.web_browser);
        url_txt = findViewById(R.id.url_txt);
        search_btn = findViewById(R.id.search_btn);
        back_btn = findViewById(R.id.back_btn);
        fav_btn = findViewById(R.id.fav_button);
        forward_btn = findViewById(R.id.forward_btn);
        home_btn = findViewById(R.id.home_btn);
        refresh_btn = findViewById(R.id.refresh_btn);
        tab_btn = findViewById(R.id.tab_btn);
        menu_btn = findViewById(R.id.menu_btn);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(100);
        progressBar.setVisibility(View.VISIBLE);
        nav_menu = findViewById(R.id.menu_item);
        nav_View = findViewById(R.id.nav_view);
        display_siteInfo = nav_View.getHeaderView(0).findViewById(R.id.display_siteInfo);
        display_siteUrl = nav_View.getHeaderView(0).findViewById(R.id.display_siteUrl);
        tab_siteImage = nav_View.getHeaderView(0).findViewById(R.id.tab_siteImage);
        nav_View.setNavigationItemSelectedListener(this);
        databaseHelper1 = new DatabaseHelper1(this);
        databaseHelper2 = new DatabaseHelper2(this);
        databaseHelper3 = new DatabaseHelper3(this);
        Intent tent = getIntent();
        String getUrlFromHistory = tent.getStringExtra(History.EXTRA_TXT);
        String getUrlFromBookmark = tent.getStringExtra(Bookmark.EXTRA_TEXTS);

        web_browser.setWebViewClient(new Browser_home());
        web_browser.setWebChromeClient(new MyChrome());

        if (getUrlFromHistory == null) {
            web_browser.loadUrl("https://www.google.com");
        }
        if (getUrlFromHistory != null) {
            web_browser.loadUrl(getUrlFromHistory);
        }
        if (getUrlFromBookmark != null) {
            web_browser.loadUrl(getUrlFromBookmark);
        }
        WebSettings webSettings = web_browser.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        web_browser.canGoBack();
        web_browser.canGoForward();
        registerForContextMenu(web_browser);

        if (savedInstanceState == null) {
            web_browser.post(new Runnable() {
                @Override
                public void run() {
                    loadWebsite();
                }
            });
        }

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web_browser.goBack();
            }
        });

        forward_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web_browser.goForward();
            }
        });

        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web_browser.loadUrl("https://www.google.com");
            }
        });

        refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web_browser.reload();
            }
        });

        tab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nav_menu.openDrawer(Gravity.RIGHT);
                if (nav_menu.isDrawerOpen(Gravity.RIGHT)) {
                    nav_menu.closeDrawer(Gravity.RIGHT);
                }
            }
        });

        fav_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bookmarkLink = web_browser.getUrl();
                if (databaseHelper2.selectData(bookmarkLink)) {
                    DeleteBookmark(bookmarkLink);
                    toastMessage("Bookmark removed !");
                    fav_btn.setColorFilter(Color.WHITE);
                }
                else {
                    AddBookmark(bookmarkLink);
                    toastMessage("Bookmark added !");
                    fav_btn.setColorFilter(Color.YELLOW);
                }
            }
        });

        web_browser.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String UserAgent, String contentDisposition, String mimeType, long contentLength) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        DownloadDialog(url, UserAgent, contentDisposition, mimeType);
                    }
                    else {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        int w=0;
                        while (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                            Log.d(TAG, "Storage Permission: awaiting user to enable permission");
                            w++;
                            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                toastMessage("Permission denied, please enable storage permission and try again !");
                                break;
                            }
                            if (w == 15000) {
                                break;
                            }
                        }
                        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                            DownloadDialog(url, UserAgent, contentDisposition, mimeType);
                        }
                    }
                }
                else {
                    DownloadDialog(url, UserAgent, contentDisposition, mimeType);
                }
            }
        });
    }

    public void DownloadDialog(final String url, final String UserAgent, String contentDisposition, String mimeType) {
        final String filename = URLUtil.guessFileName(url, contentDisposition, mimeType);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(filename)
                .setMessage("Do you want to download this file?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                        String cookie = CookieManager.getInstance().getCookie(url);
                        request.addRequestHeader("Cookie", cookie);
                        request.addRequestHeader("User-Agent", UserAgent);
                        request.allowScanningByMediaScanner();
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        DownloadManager manager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
                        manager.enqueue(request);
                        AddDownload(filename);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .show();
    }

    public void AddHistory(String newEntry) {
        databaseHelper1.addData(newEntry);
    }

    public void AddBookmark(String item) {
        databaseHelper2.addData(item);
    }

    public void DeleteBookmark(String item) {
        databaseHelper2.deleteData(item);
    }

    public void AddDownload(String item) {
        databaseHelper3.addData(item);
    }

    public void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        web_browser.saveState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        web_browser.restoreState(savedInstanceState);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        final WebView.HitTestResult webViewTestResult = web_browser.getHitTestResult();
        DownloadImageUrl = webViewTestResult.getExtra();
        if (webViewTestResult.getType() == WebView.HitTestResult.IMAGE_TYPE || webViewTestResult.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
            if (URLUtil.isNetworkUrl(DownloadImageUrl)) {
                menu.setHeaderTitle("Selected item");
                menu.add(0, 1, 0, "Download image").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        int Permission_all = 1;
                        String Permission[] = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
                        if (!hasPermission(MainActivity.this, Permission)) {
                            ActivityCompat.requestPermissions(MainActivity.this, Permission, Permission_all);
                            int w=0;
                            while (!hasPermission(MainActivity.this, Permission)) {
                                Log.d(TAG, "Storage Permission: awaiting user to enable permission");
                                w++;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                        toastMessage("Permission denied, please enable storage permission and try again !");
                                        break;
                                    }
                                }
                                if (w == 15000) {
                                    break;
                                }
                            }
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                                    String filename = "";
                                    String type = null;
                                    String MimeType = MimeTypeMap.getFileExtensionFromUrl(DownloadImageUrl);
                                    filename = URLUtil.guessFileName(DownloadImageUrl, DownloadImageUrl, MimeType);
                                    if (MimeType != null) {
                                        type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeType);
                                    }
                                    if (type == null) {
                                        filename = filename.replace(filename.substring(filename.lastIndexOf(".")), ".png");
                                        type = "image/*";
                                    }
                                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(DownloadImageUrl));
                                    request.allowScanningByMediaScanner();
                                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
                                    DownloadManager manager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
                                    manager.enqueue(request);
                                    AddDownload(filename);
                                }
                            }
                        }
                        else {
                            String filename = "";
                            String type = null;
                            String MimeType = MimeTypeMap.getFileExtensionFromUrl(DownloadImageUrl);
                            filename = URLUtil.guessFileName(DownloadImageUrl, DownloadImageUrl, MimeType);
                            if (MimeType != null) {
                                type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeType);
                            }
                            if (type == null) {
                                filename = filename.replace(filename.substring(filename.lastIndexOf(".")), ".png");
                                type = "image/*";
                            }
                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(DownloadImageUrl));
                            request.allowScanningByMediaScanner();
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
                            DownloadManager manager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
                            manager.enqueue(request);
                            AddDownload(filename);
                        }
                        return false;
                    }
                });

                menu.add(0, 2, 0, "Copy image address").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        String CopyImageUrl = webViewTestResult.getExtra();
                        ClipboardManager manager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clipData = ClipData.newPlainText("label", CopyImageUrl);
                        manager.setPrimaryClip(clipData);
                        toastMessage("Image address copied");
                        return false;
                    }
                });
            }
        }
    }

    public static boolean hasPermission(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void loadWebsite() {
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = url_txt.getText().toString();
                if (url.contains("http://")) {
                    web_browser.loadUrl(url);
                } else if (url.contains("https://")) {
                    web_browser.loadUrl(url);
                } else if (url.contains(".")) {
                    web_browser.loadUrl("http://" + url);
                } else {
                    web_browser.loadUrl("https://www.google.com/search?q=" + url);
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_bookmark:
                Intent bookmark_intent = new Intent(MainActivity.this, Bookmark.class);
                startActivity(bookmark_intent);
                break;
            case R.id.nav_history:
                Intent history_intent = new Intent(MainActivity.this, History.class);
                startActivity(history_intent);
                break;
            case R.id.nav_downloads:
                Intent download_intent = new Intent(MainActivity.this, Downloads.class);
                startActivity(download_intent);
                break;
            case R.id.nav_about:
                Intent about_intent = new Intent(MainActivity.this, About.class);
                startActivity(about_intent);
                break;
        }
        nav_menu.closeDrawer(Gravity.RIGHT);
        return true;
    }

    public void onBackPressed() {
        if (nav_menu.isDrawerOpen(Gravity.RIGHT)) {
            nav_menu.closeDrawer(Gravity.RIGHT);
        }
        else {
            if (backPressedTime + 2000 > System.currentTimeMillis()) {
                backToast.cancel();
                finish();
            }
            else {
                backToast = Toast.makeText(getBaseContext(), "Press back again to close", Toast.LENGTH_SHORT);
                backToast.show();
            }
            backPressedTime = System.currentTimeMillis();
        }
    }

    
    class Browser_home extends WebViewClient {
        Browser_home() {
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }

    public class MyChrome extends WebChromeClient {
        private View web_customView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;
        private boolean isVideoFullscreen;

        MyChrome() {
        }

        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            progressBar.setProgress(newProgress);
            if (newProgress == 100) {
                progressBar.setVisibility(ProgressBar.GONE);
            }
            else {
                progressBar.setVisibility(ProgressBar.VISIBLE);
            }
        }

        public void onReceivedTitle(WebView view, String mTitle) {
            super.onReceivedTitle(view, mTitle);
            display_siteInfo.setText(view.getTitle());
            display_siteUrl.setText(view.getUrl());
            SiteTitleParse1 = view.getTitle();
            SiteLinkParse1 = view.getUrl();
            String siteTitle = SiteTitleParse1;
            String siteLink = SiteLinkParse1;
            getSiteInfo(siteTitle, siteLink);
            if (web_browser.getUrl().equals("https://www.google.com/")) {
                url_txt.setText(null);
            }
            else {
                url_txt.setText(web_browser.getUrl());
                String url_h = web_browser.getUrl();
                AddHistory(url_h);
            }

            String bookmarkLink = web_browser.getUrl();
            if (databaseHelper2.selectData(bookmarkLink)) {
                fav_btn.setColorFilter(Color.YELLOW);
            }
            if (!databaseHelper2.selectData(bookmarkLink)) {
                fav_btn.setColorFilter(Color.WHITE);
            }
        }

        public void onReceivedIcon(WebView view, Bitmap icon) {
            BitmapDrawable iconDrawable = new BitmapDrawable(getResources(), icon);
            super.onReceivedIcon(view, icon);
            tab_siteImage.setImageBitmap(icon);
            SiteLogoParse1 = icon;
            Bitmap siteLogo = SiteLogoParse1;
            getSiteLogo(siteLogo);
        }

        public boolean isVideoFullscreen() {
            return isVideoFullscreen;
        }

        public Bitmap getDefaultVideoPoster() {
            if (web_customView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(getApplicationContext().getResources(), 2130837573);
        }

        public void onHideCustomView() {
            if (isVideoFullscreen) {
                ((FrameLayout) getWindow().getDecorView()).removeView(this.web_customView);
                this.web_customView = null;
                isVideoFullscreen = false;
                getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
                if (web_browser.getUrl().contains("youtube.com")) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                else {
                    setRequestedOrientation(this.mOriginalOrientation);
                }
                this.mCustomViewCallback.onCustomViewHidden();
                this.mCustomViewCallback = null;
            }
        }

        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback) {
            if (paramView instanceof FrameLayout) {
                FrameLayout frameLayout = (FrameLayout) paramView;
                View focusedChild = frameLayout.getFocusedChild();
                this.isVideoFullscreen = true;
                this.mFullscreenContainer = frameLayout;
                this.mCustomViewCallback = paramCustomViewCallback;
                if (focusedChild instanceof VideoView) {
                    VideoView videoView = (VideoView) focusedChild;
                }
                if (this.web_customView != null) {
                    onHideCustomView();
                    return;
                }
                this.web_customView = paramView;
                this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
                if (web_browser.getUrl().contains("youtube.com")) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    this.mCustomViewCallback = paramCustomViewCallback;
                    ((FrameLayout) getWindow().getDecorView()).addView(this.web_customView, new FrameLayout.LayoutParams(-1, -1));
                    getWindow().getDecorView().setSystemUiVisibility(3846);
                }
                else {
                    this.mOriginalOrientation = getRequestedOrientation();
                    this.mCustomViewCallback = paramCustomViewCallback;
                    ((FrameLayout) getWindow().getDecorView()).addView(this.web_customView, new FrameLayout.LayoutParams(-1, -1));
                    getWindow().getDecorView().setSystemUiVisibility(3846);
                }
            }
        }
    }
}
