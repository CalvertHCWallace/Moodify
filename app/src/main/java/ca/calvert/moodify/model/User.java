package ca.calvert.moodify.model;

public class User {
    private String userId;
    private String email;
    private String password;

    // Constructor
    public User(String userId, String email, String password) {
        this.userId = userId;
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

