package ca.calvert.moodify.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import ca.calvert.moodify.R;
import ca.calvert.moodify.views.fragments.LoginFragment;
import ca.calvert.moodify.views.fragments.RegisterFragment;

public class AuthActivity extends AppCompatActivity {

    // Declare an instance of FirebaseAuth
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        if (mAuth.getCurrentUser() != null) {
            // User is signed in, check if they exist in Firestore
            db.collection("users").document(mAuth.getCurrentUser().getUid()).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // User exists in Firestore, navigate to HomeActivity
                                Intent intent = new Intent(this, HomeActivity.class);
                                startActivity(intent);
                                finish(); // Optional - if you want to remove this activity from the stack
                            } else {
                                // User does not exist in Firestore, show login fragment
                                showLoginFragment();
                            }
                        } else {
                            // Error checking Firestore, show login fragment
                            showLoginFragment();
                        }
                    });
        } else {
            // No user is signed in, show login fragment
            showLoginFragment();
        }

    }

    public void showLoginFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new LoginFragment());
        transaction.commit();
    }

    public void showRegisterFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new RegisterFragment());
        transaction.commit();
    }
}
