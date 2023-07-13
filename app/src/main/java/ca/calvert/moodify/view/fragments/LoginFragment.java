package ca.calvert.moodify.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ca.calvert.moodify.R;
import ca.calvert.moodify.view.activities.MainActivity;

public class LoginFragment extends Fragment {

    private Button registerButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        registerButton = view.findViewById(R.id.register);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Communicate with the MainActivity to switch the fragment
                ((MainActivity) getActivity()).showRegisterFragment();
            }
        });

        return view;
    }
}

