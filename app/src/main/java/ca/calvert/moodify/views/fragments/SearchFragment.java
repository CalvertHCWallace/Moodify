package ca.calvert.moodify.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ca.calvert.moodify.R;
import ca.calvert.moodify.models.Image;
import ca.calvert.moodify.viewmodels.SearchViewModel;
import ca.calvert.moodify.views.adapters.ImageAdapter;

public class SearchFragment extends Fragment {

    private static final int NUMBER_OF_COLUMNS = 2;
    private RecyclerView recyclerView;
    private SearchViewModel viewModel;
    private ImageAdapter imageAdapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        initializeRecyclerView(view);
        initializeViewModel();

        return view;
    }

    private void initializeRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        setupLayoutManager();
        setupAdapter();
    }

    private void setupLayoutManager() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), NUMBER_OF_COLUMNS);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setupAdapter() {
        imageAdapter = new ImageAdapter();
        recyclerView.setAdapter(imageAdapter);
    }

    private void initializeViewModel() {
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        observeImageChanges();
        searchImages();
    }

    private void observeImageChanges() {
        viewModel.getImages().observe(getViewLifecycleOwner(), images -> imageAdapter.setImages(images));
    }

    private void searchImages() {
        viewModel.searchImages("");  // for now search with an empty string
    }
}