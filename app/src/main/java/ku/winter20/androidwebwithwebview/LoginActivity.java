package ku.winter20.androidwebwithwebview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
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
                doLoginToBB(username, password);
            }
        });
    }

    private void doLoginToPortal(final String username, final String password) {
        final WebView loginWebview = findViewById(R.id.loginWebview);

        WebSettings loginWebSettings = loginWebview.getSettings();
        loginWebSettings.setJavaScriptEnabled(true);
        loginWebSettings.setDomStorageEnabled(true);

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

    private void doLoginToBB(final String username, final String password) {
        final WebView loginWebview = findViewById(R.id.loginWebview);
        final WebView loginWebview2 = findViewById(R.id.loginWebview2);

        WebSettings loginWebSettings = loginWebview.getSettings();
        loginWebSettings.setJavaScriptEnabled(true);
        loginWebSettings.setDomStorageEnabled(true);

        loginWebview.addJavascriptInterface(new MyJavascriptInterface(), "Android");

        loginWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                String curURL = loginWebview.getUrl();

                if (curURL.contains("https://kulms.korea.ac.kr") && !curURL.contains("ultra")) {
                    view.loadUrl("javascript:(function() { " +
                            "document.getElementsByTagName(\"button\")[0].click();" +
                            "})()");
                }

                if (curURL.equals("https://sso.korea.ac.kr/saml/Auth.do")) {
                    view.loadUrl("javascript:(function() { " +
                            "document.getElementById('one_id').value = '" + username + "';" +
                            "document.querySelector('input[name=\"user_password\"]').value = '" + password + "';" +
                            "document.querySelector('div.form-button > div:not(hidden) > button').click();" +
                            "})()");
                }

                if (curURL.contains("https://kulms.korea.ac.kr/ultra")) {
                    loginWebview2.loadUrl("https://kulms.korea.ac.kr");

                    Log.d("BBLogin", "Successful");
                }

                Log.d(TAG, curURL);
            }
        });



        WebSettings loginWeb2Settings = loginWebview2.getSettings();
        loginWeb2Settings.setJavaScriptEnabled(true);
        loginWeb2Settings.setDomStorageEnabled(true);

        loginWebview2.addJavascriptInterface(new MyJavascriptInterface(), "Android");

        loginWebview2.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                String curURL = loginWebview2.getUrl();

                Log.d(TAG, curURL);
            }
        });

        loginWebview.loadUrl("http://kulms.korea.ac.kr/");

        Log.d(TAG, "username: " + username + "   password :" + password);
    }
}
