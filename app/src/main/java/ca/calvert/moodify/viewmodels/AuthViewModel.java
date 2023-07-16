package ca.calvert.moodify.viewmodels;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import ca.calvert.moodify.models.Profile;

public class AuthViewModel extends ViewModel {
    // Firebase components
    private final FirebaseAuth mAuth;
    private final FirebaseFirestore db;

    // LiveData to handle asynchronous operations
    private final MutableLiveData<FirebaseUser> registrationSuccessful;
    private final MutableLiveData<FirebaseUser> loginSuccessful;

    // Constructor
    public AuthViewModel() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        registrationSuccessful = new MutableLiveData<>();
        loginSuccessful = new MutableLiveData<>();
    }

    // Getters
    public LiveData<FirebaseUser> getRegistrationStatus() {
        return registrationSuccessful;
    }

    public LiveData<FirebaseUser> getLoginStatus() {
        return loginSuccessful;
    }

    // Public methods
    public void registerUser(String email, String password) {
        createUserWithEmailAndPassword(email, password);
    }

    public void loginUser(String email, String password) {
        signInUserWithEmailAndPassword(email, password);
    }

    // Private methods
    private void createUserWithEmailAndPassword(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        sendEmailVerificationToUser();
                    } else {
                        registrationSuccessful.postValue(null);
                    }
                });
    }

    private void signInUserWithEmailAndPassword(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        loginSuccessful.postValue(user);
                    } else {
                        loginSuccessful.postValue(null);
                    }
                });
    }

    private void sendEmailVerificationToUser() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(verificationTask -> {
                        if (verificationTask.isSuccessful()) {
                            createUserInFirestore(user);
                        } else {
                            // TODO: handle failed verification email sending
                        }
                    });
        }
    }

    private void createUserInFirestore(FirebaseUser user) {
        Profile profile = new Profile(user.getUid(), user.getEmail());
        db.collection("users").document(user.getUid()).set(profile.toMap())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        registrationSuccessful.postValue(user);
                    } else {
                        registrationSuccessful.postValue(null);
                    }
                });
    }
}
