// SearchViewModel.java

package ca.calvert.moodify.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import ca.calvert.moodify.models.Image;
import ca.calvert.moodify.services.NetworkManager;

// ViewModel for managing and storing UI-related data for the Search activity.
public class SearchViewModel extends AndroidViewModel {

    // Manager for network operations.
    private final NetworkManager networkManager;

    // LiveData for holding images.
    private final MutableLiveData<ArrayList<Image>> images;

    // Constructor for initializing the ViewModel.
    public SearchViewModel(Application application) {
        super(application);

        // Initialize the network manager and images LiveData.
        networkManager = new NetworkManager(application.getApplicationContext(), "");
        images = new MutableLiveData<>();
    }

    // Method to fetch images based on the search query.
    public void searchImages(String query) {
        networkManager.fetchImages(query, images);
    }

    // Getter for images LiveData.
    public LiveData<ArrayList<Image>> getImages() {
        return images;
    }
}
