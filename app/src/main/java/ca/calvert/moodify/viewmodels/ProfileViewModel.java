package ca.calvert.moodify.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileViewModel extends ViewModel {

    // Firebase components
    private final FirebaseAuth firebaseAuth;
    private final FirebaseFirestore firebaseFirestore;

    // LiveData for observing the account deletion status
    private final MutableLiveData<Boolean> deleteStatus;

    // Constructor initializing Firebase components and LiveData
    public ProfileViewModel() {
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.deleteStatus = new MutableLiveData<>();
    }

    // Getter for deleteStatus LiveData
    public LiveData<Boolean> getDeleteStatus() {
        return deleteStatus;
    }

    // Public method to delete an account
    public void deleteAccount() {
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
            // Delete user data from Firestore
            deleteUserDataFromFirestore(user.getUid());

            // Delete user from Firebase Authentication
            deleteUserFromFirebaseAuth(user);
        }
    }

    // Private helper method to delete user data from Firestore
    private void deleteUserDataFromFirestore(String userId) {
        firebaseFirestore.collection("users").document(userId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    // TODO: handle successful deletion of user data
                })
                .addOnFailureListener(e -> {
                    // TODO: handle failure in deletion of user data
                });
    }

    // Private helper method to delete user from Firebase Authentication
    private void deleteUserFromFirebaseAuth(FirebaseUser user) {
        user.delete()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Update deletion status
                        deleteStatus.setValue(true);
                    } else {
                        // Update deletion status
                        deleteStatus.setValue(false);
                    }
                });
    }
}
