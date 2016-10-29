package com.example.sherman.sbook.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sherman.sbook.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

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

    private void login() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String user = getUser(username, password);

        if (!user.equals("")) {
            SharedPreferences sp = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("userId", user);
            editor.apply();

            openMainActivity();
        } else {
            Toast.makeText(getApplicationContext(), "Username or password is wrong. Please try again!", Toast.LENGTH_SHORT).show();
        }
    }

    private String getUser(String username, String password) {
        return "01vKuHSGJTV4Lx9sswhuWc30BC12";

//        if (!username.equals("") && !password.equals("") && password.equals("123456")) {
//            if (username.equals("testuser1"))
//                return "01vKuHSGJTV4Lx9sswhuWc30BC12";
//            else if (username.equals("testuser2"))
//                return "NDJah5ROVaRkrunxSQDaEX7h7Ph2";
//            return "";
//        }
//
//        return "";
    }

    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
