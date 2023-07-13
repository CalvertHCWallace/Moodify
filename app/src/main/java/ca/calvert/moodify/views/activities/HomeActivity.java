package ca.calvert.moodify.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import ca.calvert.moodify.R;
import ca.calvert.moodify.views.fragments.MoodBoardFragment;
import ca.calvert.moodify.views.fragments.SearchFragment;
import ca.calvert.moodify.views.fragments.ProfileFragment;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Get instance of FirebaseAuth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        // Load the first Fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new MoodBoardFragment())
                .commit();

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.moodBoardItem) {
                selectedFragment = new MoodBoardFragment();
            } else if (item.getItemId() == R.id.searchItem) {
                selectedFragment = new SearchFragment();
            } else if (item.getItemId() == R.id.profileItem) {
                selectedFragment = new ProfileFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            }

            return true;  // Indicates that the event is handled
        });

    }
}
