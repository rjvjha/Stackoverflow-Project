package com.example.rajeevjha.stackoverflow;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.rajeevjha.stackoverflow.data.PreferenceHelper;
import com.example.rajeevjha.stackoverflow.utils.Consts;

public class WebViewActivity extends AppCompatActivity {

    private static final String LOG_TAG = WebViewActivity.class.getSimpleName();
    public static String EXTRA_ACTION_TOKEN_URL = "TokenUrl";
    private WebView mBrowser;
    private ProgressBar mProgressBar;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        mProgressBar = findViewById(R.id.progress_spinner);
        mBrowser = findViewById(R.id.webview);

        // check for new user login
        if (!PreferenceHelper.getLoginCheck()) {
            // clear browser cookies
            clearCookies();
        }


        // enabling JavaScript
        mBrowser.getSettings().setJavaScriptEnabled(true);
        mBrowser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        mBrowser.setWebChromeClient(new WebChromeClient() {
            // Show loading progress in activity's title bar.
            @Override
            public void onProgressChanged(WebView view, int progress) {
                setProgress(progress * 100);

                if (progress >= 100) {
                    //setProgressBarIndeterminateVisibility(false);
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    //setProgressBarIndeterminateVisibility(true);
                    mProgressBar.setVisibility(View.VISIBLE);
                }
            }
        });

        mBrowser.setWebViewClient(new WebViewClient() {
            // When start to load page, show url in activity's title bar

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d(LOG_TAG, "Auth URL: " + url);

                return url.contains("#access_token");

            }


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.d(LOG_TAG, "Loading URL: " + url);
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d(LOG_TAG, "Loaded URL: " + url);
                if (url.contains("#access_token")) {

                    mProgressDialog = new ProgressDialog(WebViewActivity.this);
                    mProgressDialog.setIndeterminate(true);
                    mProgressDialog.setMessage("Loading...");
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.setCanceledOnTouchOutside(false);
                    mProgressDialog.show();

                    String token_str = url;
                    Log.d(LOG_TAG, "Token URL: " + token_str);
                    String[] str = token_str.split("access_token=");
                    Log.d(LOG_TAG, "srt[0]: " + str[0]);
                    Log.d(LOG_TAG, "srt[1]: " + str[1]);
                    String token = str[1].substring(0, str[1].length() - 14);
                    Log.d(LOG_TAG, "token: " + token);

                    if (mProgressDialog.isShowing() && mProgressDialog != null)
                        mProgressDialog.dismiss();

                    Toast.makeText(WebViewActivity.this,
                            "Login Successful!", Toast.LENGTH_SHORT).show();

                    finishAuthentication(token);

                }
            }

        });

        mBrowser.loadUrl(getQueryUrl());


    }


    private void finishAuthentication(String token) {

        // save login details to SharedPreference
        PreferenceHelper.setLoginCheck(true);
        PreferenceHelper.putString(PreferenceHelper.PREF_KEY_Token, token);

        // finally launch UserInterestActivity
        Intent intent = new Intent(this, UserInterestActivity.class);
        startActivity(intent);
        mBrowser.destroy();
        WebViewActivity.this.finish();

    }

    // private helper method to clear cookies
    private void clearCookies() {
        CookieSyncManager.createInstance(WebViewActivity.this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
    }

    // private helper method to create query string
    private String getQueryUrl() {
        Uri baseUri = Uri.parse(Consts.AUTH_BASE_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("client_id", Consts.CLIENT_ID);
        uriBuilder.appendQueryParameter("scope", "no_expiry");
        uriBuilder.appendQueryParameter("redirect_uri", Consts.REDIRECT_URI);
        return uriBuilder.toString();
    }
}
