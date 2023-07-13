package ca.calvert.moodify.models;

import java.util.ArrayList;
import java.util.List;

public class MoodBoard {
    private String moodBoardId;
    private String moodBoardName;
    private List<String> imageIds;
    private String userId;

    // Constructor


    public MoodBoard() {
    }

    public MoodBoard(String moodBoardId, String moodBoardName, String userId) {
        this.moodBoardName = moodBoardName;
        this.userId = userId;
    }

    public String getMoodBoardId() {
        return moodBoardId;
    }

    public void setMoodBoardId(String moodBoardId) {
        this.moodBoardId = moodBoardId;
    }

    public String getMoodBoardName() {
        return moodBoardName;
    }

    public void setMoodBoardName(String moodBoardName) {
        this.moodBoardName = moodBoardName;
    }

    public List<String> getImages() {
        return imageIds;
    }

    public void setImages(List<String> imageIds) {
        this.imageIds = imageIds;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    // Methods
    public void addImage(String imageId) {
        if (this.imageIds == null) {
            this.imageIds = new ArrayList<>();
        }
        this.imageIds.add(imageId);
    }

    public void removeImage(String imageId) {
        if (this.imageIds != null) {
            this.imageIds.remove(imageId);
        }
    }


    public void rename(String name) {
        this.moodBoardName = name;
    }
}

