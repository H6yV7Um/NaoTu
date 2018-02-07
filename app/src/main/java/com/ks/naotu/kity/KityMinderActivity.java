package com.ks.naotu.kity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.ks.naotu.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KityMinderActivity extends AppCompatActivity implements OnJsKityCallback {

    @BindView(R.id.vweb)
    WebView vweb;
    JsKityInterface js = new JsKityInterface();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kity_minder);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        vweb.loadUrl("file:///android_asset/kity/index.html");
        WebSettings settings = vweb.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            settings.setAllowFileAccessFromFileURLs(true);
            settings.setAllowUniversalAccessFromFileURLs(true);
        }
        settings.setAppCacheEnabled(true);
        settings.setBuiltInZoomControls(false);
        js.setListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @SuppressLint("AddJavascriptInterface")
    @Override
    protected void onStart() {
        super.onStart();
        vweb.addJavascriptInterface(js, "kity");
    }

    @Override
    protected void onStop() {
        super.onStop();
        vweb.removeJavascriptInterface("kity");
    }

    //***************js callback start
    @Override
    public void onToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialog(String title, String content) {
        Dialog d = new Dialog(this, R.style.FilePickerTheme);
        d.setCancelable(true);
        d.setTitle(title);
        d.show();
    }
    //****js callbak end
}
