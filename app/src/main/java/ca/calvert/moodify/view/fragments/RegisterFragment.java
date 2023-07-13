package ca.calvert.moodify.view.fragments;

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
import ca.calvert.moodify.view.activities.AuthActivity;
import ca.calvert.moodify.view.activities.HomeActivity;
import ca.calvert.moodify.viewmodel.AuthViewModel;

public class RegisterFragment extends Fragment {

    private EditText emailET, passwordET, rePasswordET;
    private Button registerBtn, toLoginBtn;
    private AuthViewModel authViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        emailET = view.findViewById(R.id.email);
        passwordET = view.findViewById(R.id.password);
        rePasswordET = view.findViewById(R.id.re_password);
        registerBtn = view.findViewById(R.id.register);
        toLoginBtn = view.findViewById(R.id.back_to_login);

        registerBtn.setOnClickListener(v -> {
            String email = emailET.getText().toString().trim();
            String password = passwordET.getText().toString().trim();
            String rePassword = rePasswordET.getText().toString().trim();

            if (!email.isEmpty() && !password.isEmpty() && !rePassword.isEmpty() && password.equals(rePassword)) {
                authViewModel.registerUser(email, password);
            } else {
                Toast.makeText(getActivity(), "Invalid input or passwords do not match.", Toast.LENGTH_SHORT).show();
            }
        });

        toLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Communicate with the MainActivity to switch the fragment
                ((AuthActivity) getActivity()).showLoginFragment();
            }
        });

        authViewModel.getRegistrationStatus().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                Toast.makeText(getActivity(), "Registration successful.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                 intent.putExtra("userId", user.getUid());
                startActivity(intent);
            } else {
                Toast.makeText(getActivity(), "Registration failed.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


}

