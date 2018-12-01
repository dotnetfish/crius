

package cn.cloudartisan.crius.ui.chat;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.bean.PubLinkMessage;
import cn.cloudartisan.crius.ui.base.BaseActivity;
import cn.cloudartisan.crius.ui.trend.ArticlePublishActivity;
import cn.cloudartisan.crius.widget.CustomProgressDialog;
import cn.cloudartisan.crius.widget.TextSizeSettingWindow;

public class MMWebViewActivity extends BaseActivity implements TextSizeSettingWindow.OnSizeSelectedListener {
    WebChromeClient chromeClient;
    WebViewClient client;
    String global_url;
    ProgressBar loadingProgressBar;
    CustomProgressDialog progressDialog;
    Handler progressHandler;
    WebSettings settings;
    TextSizeSettingWindow textSizeWindow;
    WebView webview;
    
    public void initComponents() {
        setDisplayHomeAsUpEnabled(true);
        setBackIcon(R.drawable.icon_actionbar_close);
        global_url = getIntent().getStringExtra("url");
        webview = (WebView)findViewById(R.id.webview);
        settings = webview.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        webview.setWebViewClient(client);
        webview.setWebChromeClient(chromeClient);
        webview.loadUrl(global_url);
        loadingProgressBar = (ProgressBar)findViewById(R.id.loadingProgressBar);
        textSizeWindow = new TextSizeSettingWindow(this,this);
    }
    
    public int getConentLayout() {
        return R.layout.activity_webview;
    }
    
    public int getActionBarTitle() {
        return 0x0;
    }
    
    public void onClick(View v) {
    }
    
    public void onBackPressed() {
        if(webview.canGoBack()) {
            webview.goBack();
            return;
        }
        super.onBackPressed();
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.web_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_copy_url:
            {
                ClipboardManager cmb = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setPrimaryClip(ClipData.newPlainText(null, global_url));
                break;
            }
            case R.id.menu_open_browser:
            {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(Uri.parse(global_url));
                startActivity(intent);
                break;
            }
            case R.id.menu_share_moment:
            {
                PubLinkMessage link = new PubLinkMessage();
                link.title = webview.getTitle();
                link.link = webview.getUrl();
                Intent intent1 = new Intent(this, ArticlePublishActivity.class);
                intent1.putExtra(PubLinkMessage.NAME, link);
                startActivity(intent1);
                break;
            }
            case R.id.menu_text_size:
            {
                textSizeWindow.showAtLocation(webview, 0x50, 0x0, 0x0);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void onSizeSelected(int size) {
        settings.setTextZoom(size);
    }
}
