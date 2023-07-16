package ca.calvert.moodify.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import ca.calvert.moodify.R;
import ca.calvert.moodify.views.fragments.CollectionFragment;
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
                .replace(R.id.fragmentContainer, new CollectionFragment())
                .commit();

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.moodBoardItem) {
                selectedFragment = new CollectionFragment();
            } else if (item.getItemId() == R.id.searchItem) {
                selectedFragment = new SearchFragment();
            } else if (item.getItemId() == R.id.profileItem) {
                // Check if there's a user signed in
                if (mAuth.getCurrentUser() != null) {
                    // Create a ProfileFragment and pass the user ID to it
                    selectedFragment = ProfileFragment.newInstance(mAuth.getCurrentUser().getUid());
                } else {
                    // There's no user signed in. You may want to navigate to a login page instead.
                    // For now, just don't create a ProfileFragment.
                }
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectedFragment).commit();
            }

            return true;  // Indicates that the event is handled
        });

    }
}

