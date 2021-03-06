package com.example.sherman.sbook.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sherman.sbook.R;
import com.example.sherman.sbook.constants.Constants;
import com.google.firebase.auth.TwitterAuthCredential;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        if (isLoggin())
            openMainActivity();

        // Hide keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    login();
            }
        });
    }

    public boolean isLoggin() {
        SharedPreferences sp = getSharedPreferences(Constants.RefName, Context.MODE_PRIVATE);
        String userId = sp.getString(Constants.UserID, "");

        if (!userId.equals(""))
            return true;

        return false;
    }

    private void login() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String user = getUser(username, password);

        if (!user.equals("")) {
            SharedPreferences sp = getSharedPreferences(Constants.RefName, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(Constants.UserID, user);
            editor.apply();

            openMainActivity();
        } else {
            Toast.makeText(getApplicationContext(), "Username or password is wrong. Please try again!", Toast.LENGTH_SHORT).show();
        }
    }

    private String getUser(String username, String password) {
        if (!username.equals("") && !password.equals("")) {
            if (username.equals("u1"))
                return "01vKuHSGJTV4Lx9sswhuWc30BC12";
            else if (username.equals("u2"))
                return "NDJah5ROVaRkrunxSQDaEX7h7Ph2";
            return "";
        }

        return "";
    }

    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}