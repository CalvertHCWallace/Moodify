package ca.calvert.moodify.models;

import java.util.ArrayList;
import java.util.List;

public class MoodBoard {
    private String id;
    private String name;
    private List<String> imageIds;
    private String userId;
    private boolean isPublic;

    // Constructor


    public MoodBoard() {
    }

    public MoodBoard(String name, String userId, boolean isPublic) {
        this.name = name;
        this.userId = userId;
        this.isPublic = isPublic;
    }

    public String getId() {
        return id;
    }

    public void setId(String moodBoardId) {
        this.id = moodBoardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
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
        this.name = name;
    }
}

