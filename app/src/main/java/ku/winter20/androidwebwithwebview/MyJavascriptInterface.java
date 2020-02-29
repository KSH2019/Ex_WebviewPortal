package ku.winter20.androidwebwithwebview;

import android.util.Log;
import android.webkit.JavascriptInterface;

public class MyJavascriptInterface {
    private String TAG = "MyJavascriptInterface";

    @JavascriptInterface
    public void getHtml(String html) {
        String source = html;
        Log.d(TAG, html);
    }
}