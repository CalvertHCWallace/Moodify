package ca.calvert.moodify.views.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import ca.calvert.moodify.R;
import ca.calvert.moodify.models.Profile;
import ca.calvert.moodify.viewmodels.ProfileViewModel;
import ca.calvert.moodify.views.activities.AuthActivity;

// Fragment showing user's profile
public class ProfileFragment extends Fragment {

    private Profile profile;
    private TextView userInfoTextView;
    private Button deleteAccountButton;
    private Button logoutButton;
    private ProfileViewModel viewModel;

    // Firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String userId) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString("userId", userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize UI elements
        userInfoTextView = view.findViewById(R.id.userInfoTextView);
        deleteAccountButton = view.findViewById(R.id.deleteAccountButton);
        logoutButton = view.findViewById(R.id.logoutButton);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        // Fetch and display user data
        fetchAndDisplayUserData();

        // Handle delete account
        handleDeleteAccount();

        // Handle logout
        handleLogout();

        return view;
    }

    // Fetch user data and display it
    private void fetchAndDisplayUserData() {
        String userId = getArguments().getString("userId");

        viewModel.getDeleteStatus().observe(getViewLifecycleOwner(), isSuccess -> {
            if (isSuccess != null && isSuccess) {
                logoutAndNavigateToLogin();
            } else {
                Toast.makeText(getContext(), "Error deleting account", Toast.LENGTH_SHORT).show();
            }
        });

        db.collection("users").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    profile = documentSnapshot.toObject(Profile.class);
                    if (profile != null) {
                        userInfoTextView.setText("User ID: " + profile.getUserId() + "\nUsername: " + profile.getEmail());
                    } else {
                        Toast.makeText(getContext(), "Error retrieving user data", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    // Handle account deletion
    private void handleDeleteAccount() {
        deleteAccountButton.setOnClickListener(v -> new AlertDialog.Builder(getContext())
                .setTitle("Delete account")
                .setMessage("Are you sure you want to delete your account?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> viewModel.deleteAccount())
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show());
    }

    // Handle user logout
    private void handleLogout() {
        logoutButton.setOnClickListener(v -> new AlertDialog.Builder(getContext())
                .setTitle("Log out")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> logoutAndNavigateToLogin())
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show());
    }

    // Logout and navigate to login activity
    private void logoutAndNavigateToLogin() {
        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(getContext(), AuthActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
