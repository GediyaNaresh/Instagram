package com.nareshgediya.instagram;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Switch;

import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.nareshgediya.instagram.Fragments.HomeFragment;
import com.nareshgediya.instagram.Fragments.NotificationFragment;
import com.nareshgediya.instagram.Fragments.ProfileFragment;
import com.nareshgediya.instagram.Fragments.SearchFragment;


public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;

    private BottomNavigationView bottomNavigationView;
    private Fragment selectorFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();

        bottomNavigationView = findViewById(R.id.nav_view);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_nav:
                        selectorFragment = new HomeFragment();
                        break;
                    case R.id.serach_nav:
                        selectorFragment = new SearchFragment();
                        break;
                    case R.id.add_nav:
                        selectorFragment = null;
                        startActivity(new Intent(MainActivity.this, PostActivity.class));
                        finish();
                        break;
                    case R.id.notify_nav:
                        selectorFragment = new NotificationFragment();
                        break;
                    case R.id.profile_nav:
                        selectorFragment = new ProfileFragment();
                        break;
                }
                if (selectorFragment != null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment,selectorFragment).commit();
                }
                return true;
            }
        });



        Bundle intent = getIntent().getExtras();
        if (intent != null) {
            String profileId = intent.getString("publisherId");

            getSharedPreferences("PROFILE", MODE_PRIVATE).edit().putString("profileId", profileId).apply();

            getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new ProfileFragment()).commit();
            bottomNavigationView.setSelectedItemId(R.id.profile_nav);
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment , new HomeFragment()).commit();
        }
    }


}