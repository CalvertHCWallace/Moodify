package ca.calvert.moodify.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.calvert.moodify.listeners.OnMoodBoardFetchedListener;
import ca.calvert.moodify.listeners.OnProfileFetchedListener;
import ca.calvert.moodify.models.MoodBoard;
import ca.calvert.moodify.models.Profile;

public class CollectionViewModel extends ViewModel {

    // Firestore component
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    // LiveData to handle asynchronous operations
    private MutableLiveData<List<MoodBoard>> moodBoardsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<MoodBoard>> userMoodBoardsLiveData = new MutableLiveData<>();

    // Getters for LiveData
    public LiveData<List<MoodBoard>> getMoodBoardsLiveData() {
        return moodBoardsLiveData;
    }

    public LiveData<List<MoodBoard>> getUserMoodBoardsLiveData() {
        return userMoodBoardsLiveData;
    }

    // Public methods for interacting with the ViewModel from outside
    public void addMoodBoard(String moodBoardName, String userId, boolean isPublic, Profile userProfile) {
        createNewMoodBoard(moodBoardName, userId, isPublic, userProfile);
    }

    public void updateUserProfile(Profile userProfile) {
        updateProfileInFirestore(userProfile);
    }

    public void fetchUserProfile(String userId, final OnProfileFetchedListener listener) {
        fetchProfileFromFirestore(userId, listener);
    }

    public void fetchUserMoodBoards(String userId) {
        fetchUserMoodBoardsFromFirestore(userId);
    }

    // Private helper methods
    private void createNewMoodBoard(String moodBoardName, String userId, boolean isPublic, Profile userProfile) {
        MoodBoard newMoodBoard = new MoodBoard(moodBoardName, userId, isPublic);

        db.collection("moodBoards").add(newMoodBoard)
                .addOnSuccessListener(documentReference -> {
                    String newMoodBoardId = documentReference.getId();
                    userProfile.getMoodBoardIds().add(newMoodBoardId);
                    updateUserProfile(userProfile);
                })
                .addOnFailureListener(e -> {
                    // TODO: handle failed MoodBoard creation
                });
    }

    private void updateProfileInFirestore(Profile userProfile) {
        Map<String, Object> userProfileMap = userProfile.toMap();

        db.collection("users").document(userProfile.getUserId())
                .set(userProfileMap)
                .addOnSuccessListener(aVoid -> {
                    // TODO: handle successful update
                })
                .addOnFailureListener(e -> {
                    // TODO: handle failed update
                });
    }

    private void fetchProfileFromFirestore(String userId, final OnProfileFetchedListener listener) {
        db.collection("users").document(userId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Profile profile = documentSnapshot.toObject(Profile.class);
                        listener.onProfileFetched(profile);
                    }
                });
    }

    private void fetchUserMoodBoardsFromFirestore(String userId) {
        fetchUserProfile(userId, profile -> {
            if (profile != null) {
                processUserMoodBoards(profile);
            } else {
                // TODO: handle null profile
            }
        });
    }

    private void processUserMoodBoards(Profile profile) {
        List<MoodBoard> userMoodBoards = new ArrayList<>();
        int[] fetchCount = {0};
        int totalMoodBoards = profile.getMoodBoardIds().size();

        for (String moodBoardId : profile.getMoodBoardIds()) {
            getMoodBoardById(moodBoardId, moodBoard -> {
                if (moodBoard != null) {
                    userMoodBoards.add(moodBoard);
                }
                fetchCount[0]++;
                if (fetchCount[0] == totalMoodBoards) {
                    userMoodBoardsLiveData.setValue(userMoodBoards);
                }
            });
        }
    }

    private void getMoodBoardById(String moodBoardId, OnMoodBoardFetchedListener listener) {
        db.collection("moodBoards").document(moodBoardId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        MoodBoard moodBoard = documentSnapshot.toObject(MoodBoard.class);
                        listener.onMoodBoardFetched(moodBoard);
                    }
                });
    }
}
