package ca.calvert.moodify.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import ca.calvert.moodify.models.Profile;

public class HomeViewModel extends ViewModel {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private MutableLiveData<Profile> userLiveData = new MutableLiveData<>();

    public LiveData<Profile> getUserData() {
        return userLiveData;
    }

    public void fetchUserData(String userId) {
        DocumentReference userRef = db.collection("users").document(userId);

        userRef.get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Profile user = documentSnapshot.toObject(Profile.class);
                        userLiveData.postValue(user);
                    } else {
                        userLiveData.postValue(null);
                    }
                })
                .addOnFailureListener(e -> {
                    // handle error here
                    userLiveData.postValue(null);
                });
    }
}
