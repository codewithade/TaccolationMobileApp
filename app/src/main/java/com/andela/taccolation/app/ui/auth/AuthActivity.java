package com.andela.taccolation.app.ui.auth;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.andela.taccolation.R;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
    }
}