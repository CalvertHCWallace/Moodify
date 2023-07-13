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
import ca.calvert.moodify.view.activities.AuthActivity;

public class LoginFragment extends Fragment {

    private Button loginBtn, toRegisterBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        loginBtn = view.findViewById(R.id.login);
        toRegisterBtn = view.findViewById(R.id.register);

        toRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Communicate with the MainActivity to switch the fragment
                ((AuthActivity) getActivity()).showRegisterFragment();
            }
        });

        return view;
    }

}

