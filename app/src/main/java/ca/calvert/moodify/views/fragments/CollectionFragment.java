// CollectionFragment.java

package ca.calvert.moodify.views.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import ca.calvert.moodify.R;
import ca.calvert.moodify.models.MoodBoard;
import ca.calvert.moodify.viewmodels.CollectionViewModel;
import ca.calvert.moodify.views.adapters.MoodBoardAdapter;

// Collection Fragment showing list of mood boards
public class CollectionFragment extends Fragment {

    private CollectionViewModel viewModel;
    private FirebaseAuth mAuth;
    private MoodBoardAdapter adapter;

    // Required empty public constructor
    public CollectionFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_collection, container, false);

        // Setup Recycler view, View model and Floating action button
        setupRecyclerView(view);
        setupViewModel();
        setupFloatingActionButton(view);

        return view;
    }

    // Method to initialize and set up RecyclerView
    private void setupRecyclerView(View view) {
        adapter = new MoodBoardAdapter(new ArrayList<>());
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    // Method to setup ViewModel and LiveData observation
    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(CollectionViewModel.class);
        String userId = mAuth.getUid();
        viewModel.fetchUserMoodBoards(userId);

        viewModel.getUserMoodBoardsLiveData().observe(getViewLifecycleOwner(), moodBoards -> {
            adapter.setMoodBoards(moodBoards);
            adapter.notifyDataSetChanged();
        });
    }

    // Method to setup floating action button
    private void setupFloatingActionButton(View view) {
        FloatingActionButton fab = view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(this::showAddMoodBoardDialog);
    }

    // Method to show dialog for adding mood board
    private void showAddMoodBoardDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Mood Board");

        View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.dialog_mood_board, (ViewGroup) getView(), false);
        final EditText input = viewInflated.findViewById(R.id.input);
        final Switch aSwitch = viewInflated.findViewById(R.id.switch_public);
        builder.setView(viewInflated);

        builder.setPositiveButton(android.R.string.ok, (dialog, which) -> handlePositiveButtonClick(input, aSwitch));
        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.cancel());

        builder.show();
    }

    // Method to handle positive button click on dialog
    private void handlePositiveButtonClick(EditText input, Switch aSwitch) {
        String moodBoardName = input.getText().toString();
        boolean isPublic = aSwitch.isChecked();

        String userId = mAuth.getUid();
        viewModel.fetchUserProfile(userId, profile -> viewModel.addMoodBoard(moodBoardName, userId, isPublic, profile));
    }
}
