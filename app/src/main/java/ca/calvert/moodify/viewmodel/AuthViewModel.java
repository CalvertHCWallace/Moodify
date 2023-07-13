package ca.calvert.moodify.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

import ca.calvert.moodify.model.Profile;

public class AuthViewModel extends ViewModel {
    private final FirebaseAuth mAuth;
    private final FirebaseFirestore db;
    private final MutableLiveData<FirebaseUser> registrationSuccessful;
    private final MutableLiveData<FirebaseUser> loginSuccessful;

    public AuthViewModel() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        registrationSuccessful = new MutableLiveData<>();
        loginSuccessful = new MutableLiveData<>();
    }

    public LiveData<FirebaseUser> getRegistrationStatus() {
        return registrationSuccessful;
    }

    public LiveData<FirebaseUser> getLoginStatus() {
        return loginSuccessful;
    }

    public void registerUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            user.sendEmailVerification()
                                    .addOnCompleteListener(verificationTask -> {
                                        if (verificationTask.isSuccessful()) {
                                            createUserInFirestore(user, email);
                                        } else {
                                            // TODO: handle failed verification email sending
                                        }
                                    });
                        }
                    } else {
                        registrationSuccessful.postValue(null);
                    }
                });
    }

    private void createUserInFirestore(FirebaseUser user, String email) {
        Profile profile = new Profile(user.getUid(), email);
        db.collection("users").document(user.getUid()).set(profile.toMap())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        registrationSuccessful.postValue(user);
                    } else {
                        registrationSuccessful.postValue(null);
                    }
                });
    }

    public void loginUser(String email, String password) {
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

}
