package com.example.rajeevjha.stackoverflow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.rajeevjha.stackoverflow.data.PreferenceHelper;

public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG = LoginActivity.class.getSimpleName();
    private static final int REQUEST_ACTION = 1;
    private String token_str;
    private Button mLoginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // binding views
        mLoginButton = findViewById(R.id.login_button);


        // onClick Listener
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create a webView Intent
                Intent intent = new Intent(LoginActivity.this,
                        WebViewActivity.class);
                startActivityForResult(intent, REQUEST_ACTION);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        // navigating user to QuestionListActivity
        // check user is already loggedIn
        if (PreferenceHelper.getLoginCheck()) {
            // user is logged in
            Intent intent = new Intent(this, QuestionListActivity.class);
            startActivity(intent);
            finish();

        }
    }

    // print user token details
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        switch (requestCode) {

            case REQUEST_ACTION:
                if (resultCode == Activity.RESULT_OK) {
                    token_str = data.getExtras().getString(WebViewActivity.EXTRA_ACTION_TOKEN_URL);
                    printDebugInfo(token_str);

                }
                break;

        }


    }

    // helper method to print token information
    private void printDebugInfo(String token_str) {
        Log.d(LOG_TAG, "Token URL: " + token_str);
        String[] str = token_str.split("access_token=");
        Log.d(LOG_TAG, "srt[0]: " + str[0]);
        Log.d(LOG_TAG, "srt[1]: " + str[1]);
        String token = str[1].substring(0, str[1].length() - 14);
        Log.d(LOG_TAG, "token: " + token);

    }
}
