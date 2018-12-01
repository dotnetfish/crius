package cn.cloudartisan.crius.component;

/**
 * Created by kenqu on 2015/12/12.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.app.URLConstant;


public class BaseWebActivity extends Activity {

     private View mLoadingView;
    protected ProgressWebView mWebView;
    private ProgressBar web_progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_web_view);

        mWebView = (ProgressWebView) findViewById(R.id.baseweb_webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        initData();
    }

    private void initData() {
        Intent intent = getIntent();

        String url = intent.getStringExtra("url");
        String title=intent.getStringExtra("title");
        setTitle(title);
        if(url!=null){
            if(!url.startsWith("http")){
                url= URLConstant.DOMAIN+url;
            }
            mWebView.loadUrl(url);
        }
    }

}
