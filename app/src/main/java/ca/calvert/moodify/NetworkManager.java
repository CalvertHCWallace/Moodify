package ca.calvert.moodify;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ca.calvert.moodify.models.Image;

public class NetworkManager {
    private static final String BASE_URL = "https://www.flickr.com/services/rest/";

    private String searchWord;

    private RequestQueue requestQueue;

    public NetworkManager(Context context, String searchWord) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        this.searchWord = searchWord;
    }

    public void fetchImages(String searchWord, final MutableLiveData<ArrayList<Image>> imageLiveData) {
        this.searchWord = searchWord;
        String url = buildImageRequestUrl();
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    ArrayList<Image> imageArrayList = ImageParser.imageJsonParser(jsonObject);
                    imageLiveData.postValue(imageArrayList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(request);
    }

    private String buildImageRequestUrl() {
        if(searchWord.isEmpty()) {
            return BASE_URL + "?method=flickr.photos.getRecent&api_key=9f7237832c8dc67b22d04b6e11f4f05a&format=json&nojsoncallback=1";
        } else {
            return BASE_URL + "?method=flickr.photos.search&api_key=9f7237832c8dc67b22d04b6e11f4f05a&format=json&nojsoncallback=1&tags=" + searchWord;
        }
    }
}
