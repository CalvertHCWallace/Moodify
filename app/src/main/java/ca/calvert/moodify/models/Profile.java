package ca.calvert.moodify.models;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Profile {
    private String userId;
    private String email;
    private List<String> moodBoardIds;  // New field

    // Constructor
    public Profile() {
        moodBoardIds = new ArrayList<>();
    }

    public Profile(String userId, String email) {
        this.userId = userId;
        this.email = email;
        this.moodBoardIds = new ArrayList<>();
    }

    // Another constructor to create a User object from a Firestore DocumentSnapshot
    public Profile(DocumentSnapshot documentSnapshot) {
        this.userId = documentSnapshot.getString("userId");
        this.email = documentSnapshot.getString("email");
        this.moodBoardIds = (List<String>) documentSnapshot.get("moodBoardIds");
    }

    // Method to convert the user object into a map
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", this.userId);
        map.put("email", this.email);
        map.put("moodBoardIds", this.moodBoardIds);  // Adding moodBoardIds to map
        return map;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getMoodBoardIds() {
        return moodBoardIds;
    }

    public void setMoodBoardIds(List<String> moodBoardIds) {
        this.moodBoardIds = moodBoardIds;
    }

    // Methods
    public MoodBoard createMoodBoard(String name, boolean isPublic) {
        MoodBoard newMoodBoard = new MoodBoard(name, this.userId, isPublic);
        this.moodBoardIds.add(newMoodBoard.getId());
        return newMoodBoard;
    }

    public void editMoodBoard(String moodBoardId, String newName) {
        // Code to edit mood board
    }

    public void deleteMoodBoard(String moodBoardId) {
        this.moodBoardIds.remove(moodBoardId);  // Removing mood board id from the list
        // Code to delete mood board
    }
}
