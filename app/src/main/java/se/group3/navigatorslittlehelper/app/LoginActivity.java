package se.group3.navigatorslittlehelper.app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GitHub;

import java.io.IOException;

/**
 * Created by qw4z1 on 4/8/14.
 */
public class LoginActivity extends ActionBarActivity{
    private static final String TAG = LoginActivity.class.getSimpleName();

    private EditText mUsernameEdittext;
    private EditText mPasswordEdittext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUsernameEdittext = (EditText) findViewById(R.id.login_username_edittext);
        mPasswordEdittext = (EditText) findViewById(R.id.login_password_edittext);
        final Button button = (Button) findViewById(R.id.login_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        String username = mUsernameEdittext.getText().toString();
        String password = mPasswordEdittext.getText().toString();

        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            new LoginTask().execute(username, password);
        }
    }

    private class LoginTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            boolean returnValue;
            try {
                GitHub github = GitHub.connectUsingPassword(params[0], params[1]);
                Log.d(TAG, "github is null: " + (github== null));
                GHMyself myself = github.getMyself();
                Log.d(TAG, "myself is null: " + (myself== null));
                returnValue = github.isCredentialValid();
            } catch (IOException e) {
                return false;
            }
            return returnValue;
        }

        @Override
        protected void onPostExecute(Boolean returnValue) {
            if(!returnValue.booleanValue()) {
                Toast.makeText(LoginActivity.this, "Login failed.", Toast.LENGTH_LONG).show();
                return;
            }
            Toast.makeText(LoginActivity.this, "Login succeeded.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}
