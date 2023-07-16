package ca.calvert.moodify.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ca.calvert.moodify.R;
import ca.calvert.moodify.views.activities.AuthActivity;
import ca.calvert.moodify.views.activities.HomeActivity;
import ca.calvert.moodify.viewmodels.AuthViewModel;

public class RegisterFragment extends Fragment {

    private EditText emailET, passwordET, rePasswordET;
    private Button registerBtn, toLoginBtn;
    private AuthViewModel authViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        initViewModel();
        initUIElements(view);
        setupRePasswordEditorAction();
        setupRegisterButton();
        setupLoginButton();
        observeRegistrationStatus();

        return view;
    }

    private void initViewModel() {
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
    }

    private void initUIElements(View view) {
        emailET = view.findViewById(R.id.email);
        passwordET = view.findViewById(R.id.password);
        rePasswordET = view.findViewById(R.id.re_password);
        registerBtn = view.findViewById(R.id.register);
        toLoginBtn = view.findViewById(R.id.back_to_login);
    }

    private void setupRePasswordEditorAction() {
        rePasswordET.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                registerBtn.performClick();
                return true;
            }
            return false;
        });
    }

    private void setupRegisterButton() {
        registerBtn.setOnClickListener(v -> {
            String email = emailET.getText().toString().trim();
            String password = passwordET.getText().toString().trim();
            String rePassword = rePasswordET.getText().toString().trim();

            if (validateInput(email, password, rePassword)) {
                authViewModel.registerUser(email, password);
            } else {
                Toast.makeText(getActivity(), "Invalid input or passwords do not match.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateInput(String email, String password, String rePassword) {
        return !email.isEmpty() && !password.isEmpty() && !rePassword.isEmpty() && password.equals(rePassword);
    }

    private void setupLoginButton() {
        toLoginBtn.setOnClickListener(v -> ((AuthActivity) getActivity()).showLoginFragment());
    }

    private void observeRegistrationStatus() {
        authViewModel.getRegistrationStatus().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                Toast.makeText(getActivity(), "Registration successful.", Toast.LENGTH_SHORT).show();
                navigateToHome(user.getUid());
            } else {
                Toast.makeText(getActivity(), "Registration failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToHome(String userId) {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }
}
