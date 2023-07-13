package ca.calvert.moodify.model;

public class Image {
    private String imageId;
    private String imageURL;
    private String title;
    private String photographer;

    // Constructor
    public Image(String imageId, String imageURL, String title, String photographer) {
        this.imageId = imageId;
        this.imageURL = imageURL;
        this.title = title;
        this.photographer = photographer;
    }

    // Getters and Setters


    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhotographer() {
        return photographer;
    }

    public void setPhotographer(String photographer) {
        this.photographer = photographer;
    }

    // Methods
    public void addImageToMoodBoard(MoodBoard moodBoard) {
        moodBoard.addImage(imageId);
    }

    public void removeImageFromMoodBoard(MoodBoard moodBoard) {
        moodBoard.removeImage(imageId);
    }

    @Override
    public String toString() {
        return "Image{" +
                "imageId='" + imageId + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", title='" + title + '\'' +
                ", photographer='" + photographer + '\'' +
                '}';
    }
}
