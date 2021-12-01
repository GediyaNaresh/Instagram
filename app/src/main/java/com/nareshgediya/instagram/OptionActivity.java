package com.nareshgediya.instagram;



import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class OptionActivity extends AppCompatActivity {

    private TextView settings;
    private TextView logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        settings = findViewById(R.id.settings);
        logOut = findViewById(R.id.logout);

        getSupportActionBar().setTitle("Options");

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                startActivity(new Intent(OptionActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
            }
        });
    }
}