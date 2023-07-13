package ca.calvert.moodify;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ca.calvert.moodify.models.Image;

public class ImageParser {
    public static ArrayList<Image> imageJsonParser(JSONObject jsonObject){
        ArrayList<Image> imageList = new ArrayList<>();
        try {
            JSONObject dataObject = jsonObject.getJSONObject("photos");
            JSONArray jsonArray = dataObject.getJSONArray("photo");
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonobject = jsonArray.getJSONObject(i);
                Image image = new Image();
                image.setImageId(jsonobject.getString("id"));
                image.setTitle(jsonobject.getString("title"));

                long serverId = jsonobject.getLong("server");
                String id = jsonobject.getString("id");
                String secret = jsonobject.getString("secret");
                String imageURL = "https://live.staticflickr.com/" + serverId + "/" + id + "_" + secret + "_" + "w.jpg";
                image.setImageURL(imageURL);

                // For the photographer field, you can modify this as per your requirement.
                // As of now, I'm setting it as an empty string since it's not available in the current JSON structure.
                image.setPhotographer("");

                imageList.add(image);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return imageList;
    }
}
