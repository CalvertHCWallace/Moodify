package ca.calvert.moodify.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ca.calvert.moodify.R;
import ca.calvert.moodify.view.activities.AuthActivity;

public class RegisterFragment extends Fragment {

    private EditText emailET, passwordET, rePasswordET;
    private Button registerBtn, toLoginBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        registerBtn = view.findViewById(R.id.register);
        toLoginBtn = view.findViewById(R.id.back_to_login);

        toLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Communicate with the MainActivity to switch the fragment
                ((AuthActivity) getActivity()).showLoginFragment();
            }
        });

        return view;
    }
}

