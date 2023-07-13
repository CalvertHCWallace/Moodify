package ca.calvert.moodify.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import ca.calvert.moodify.R;
import ca.calvert.moodify.model.Profile;

public class HomeActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String userId;
    private TextView userIdTextView, emailTextView;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Get references of your views
        userIdTextView = findViewById(R.id.userIdTextView);
        emailTextView = findViewById(R.id.emailTextView);
        logoutButton = findViewById(R.id.logoutButton);

        // Get instance of Firestore and FirebaseAuth
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        // Get user id from the intent
        userId = getIntent().getStringExtra("userId");

        if (userId != null) {
            // Fetch user data from Firestore
            fetchUserData();
        }

        // Set onClickListener for the logout button
        logoutButton.setOnClickListener(v -> {
            mAuth.signOut();
            Intent intent = new Intent(HomeActivity.this, AuthActivity.class);
            startActivity(intent);
            finish(); // Call this method to prevent user coming back to this activity on back press.
        });
    }

    private void fetchUserData() {
        DocumentReference userRef = db.collection("users").document(userId);

        userRef.get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Use the Profile class to map the document to a Java object
                        Profile user = documentSnapshot.toObject(Profile.class);

                        // Update TextViews with user data
                        if (user != null) {
                            userIdTextView.setText(String.format("User ID: %s", user.getUserId()));
                            emailTextView.setText(String.format("Email: %s", user.getEmail()));
                        }
                    } else {
                        Log.d("Firestore", "No user found with id: " + userId);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.w("Firestore", "Error getting user", e);
                    // TODO: Handle failure
                });
    }
}
