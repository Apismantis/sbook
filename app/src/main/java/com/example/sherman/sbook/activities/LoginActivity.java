package com.example.sherman.sbook.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.sherman.sbook.R;

/**
 * Created by Admin on 10/29/2016.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnLogin) {

        }
    }
}
