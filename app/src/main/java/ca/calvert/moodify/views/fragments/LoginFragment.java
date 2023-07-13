package ca.calvert.moodify.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ca.calvert.moodify.R;
import ca.calvert.moodify.views.activities.AuthActivity;
import ca.calvert.moodify.views.activities.HomeActivity;
import ca.calvert.moodify.viewmodels.AuthViewModel;

public class LoginFragment extends Fragment {

    private AuthViewModel authViewModel;
    private EditText emailEditText, passwordEditText;
    private Button loginBtn, toRegisterBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        loginBtn = view.findViewById(R.id.login);
        toRegisterBtn = view.findViewById(R.id.register);

        // Initialize EditTexts
        emailEditText = view.findViewById(R.id.email);
        passwordEditText = view.findViewById(R.id.password);

        // Initialize ViewModel
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        // Set OnClickListener for login button
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()) {
                    authViewModel.loginUser(email, password);
                } else {
                    Toast.makeText(getActivity(), "Please enter both email and password.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        toRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Communicate with the MainActivity to switch the fragment
                ((AuthActivity) getActivity()).showRegisterFragment();
            }
        });

        authViewModel.getLoginStatus().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                Toast.makeText(getActivity(), "Login successful.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                intent.putExtra("userId", user.getUid());
                startActivity(intent);
            } else {
                Toast.makeText(getActivity(), "Login failed.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}

