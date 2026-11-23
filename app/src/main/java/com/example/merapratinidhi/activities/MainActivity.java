package com.example.merapratinidhi.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.merapratinidhi.R;
import com.example.merapratinidhi.fragments.HomeFragment;
import com.example.merapratinidhi.fragments.ProfileFragment;
import com.example.merapratinidhi.fragments.RepresentativesFragment;
import com.example.merapratinidhi.fragments.SavedFragment;
import com.example.merapratinidhi.utils.ThemeHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeHelper.applyTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.nav_representatives) {
                selectedFragment = new RepresentativesFragment();
            } else if (itemId == R.id.nav_saved) {
                selectedFragment = new SavedFragment();
            } else if (itemId == R.id.nav_profile) {
                selectedFragment = new ProfileFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
                return true;
            }
            return false;
        });
    }

    public void switchToTab(int menuItemId) {
        if (bottomNavigationView != null) {
            bottomNavigationView.setSelectedItemId(menuItemId);
        }
    }
}
