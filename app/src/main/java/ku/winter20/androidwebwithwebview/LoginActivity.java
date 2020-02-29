package ku.winter20.androidwebwithwebview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private String username, password;
    private String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginBtn = (Button) findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                username = ( (EditText) findViewById(R.id.username)).getText().toString();
                password = ( (EditText) findViewById(R.id.password)).getText().toString();
                doLogin(username, password);
            }
        });
    }

    private void doLogin(final String username, final String password) {
        final WebView loginWebview = findViewById(R.id.loginWebview);

        loginWebview.getSettings().setJavaScriptEnabled(true);
        loginWebview.addJavascriptInterface(new MyJavascriptInterface(), "Android");

        loginWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                if (loginWebview.getUrl().equals("https://portal.korea.ac.kr/front/layout/mobile.jsp")) {
                    view.loadUrl("javascript:(function() { " +
                            "document.getElementById('oneid').value = '" + username + "';" +
                            "document.getElementById('password').value = '" + password + "';" +
                            "document.querySelector('article.login_inputarea > div.btn_area > button').click();"+
                            "})()");
                }

                Log.d(TAG, loginWebview.getUrl());
            }
        });

        loginWebview.loadUrl("https://portal.korea.ac.kr/");

        Log.d(TAG, "username: " + username + "   password :" + password);
    }
}