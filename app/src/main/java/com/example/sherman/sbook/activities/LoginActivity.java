package com.example.sherman.sbook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sherman.sbook.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Admin on 10/29/2016.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference UserRef;

    private EditText etUsername, etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        findViewById(R.id.btnLogin).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnLogin) {
            if (checkValid())
                openMainActivity();
        }
    }

    private boolean checkValid() {


        return true;
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
