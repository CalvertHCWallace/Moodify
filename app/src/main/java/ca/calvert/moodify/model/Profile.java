package ca.calvert.moodify.model;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class Profile {
    private String userId;
    private String email;

    // Constructor


    public Profile() {
    }

    public Profile(String userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    // Another constructor to create a User object from a Firestore DocumentSnapshot
    public Profile(DocumentSnapshot documentSnapshot) {
        this.userId = documentSnapshot.getString("userId");
        this.email = documentSnapshot.getString("email");
    }

    // Method to convert the user object into a map
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", this.userId);
        map.put("email", this.email);
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

    // Methods
    public MoodBoard createMoodBoard(String name) {
        return new MoodBoard("ID" , name, this.userId);
    }

    public void editMoodBoard(String moodBoardId, String newName) {
        // Code to edit mood board
    }

    public void deleteMoodBoard(String moodBoardId) {
        // Code to delete mood board
    }
}

