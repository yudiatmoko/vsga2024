package com.iyam.myvsgaproject2.ui.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.iyam.myvsgaproject2.R;
import com.iyam.myvsgaproject2.data.preferences.Preferences;
import com.iyam.myvsgaproject2.databinding.ActivityProfileBinding;
import com.iyam.myvsgaproject2.model.User;
import com.iyam.myvsgaproject2.ui.viewmodel.UserViewModel;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;
    private Button btnLogout, btnEditProfile;
    private UserViewModel viewModel;
    private User userLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        binding = ActivityProfileBinding.inflate(
                getLayoutInflater(),
                getWindow().getDecorView().findViewById(android.R.id.content),
                false
        );
        btnLogout = binding.btnLogout;
        btnEditProfile = binding.btnUpdateProfile;
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setOnClickListener();
        setContentForm();
        observeUser();
    }

    private void observeUser() {
        String username = Preferences.getLoggedInUser(this);
        viewModel.getUserLoggedIn(username).observe(this, user -> {
            binding.formProfile.etName.setText(user.getName());
            binding.formProfile.etUsername.setText(user.getUsername());
            binding.formProfile.etEmail.setText(user.getEmail());
            userLoggedIn = user;
        });
    }

    private void setContentForm() {
        binding.formProfile.tilPassword.setVisibility(View.GONE);
        binding.formProfile.tilConfirmPassword.setVisibility(View.GONE);
        binding.formProfile.tilUsername.setEnabled(false);
    }

    private void setOnClickListener() {
        btnLogout.setOnClickListener(view -> {
            Preferences.clearLoggedInUser(getBaseContext());
            finishAffinity();
        });
        btnEditProfile.setOnClickListener(view -> {
            updateProfile();
        });
    }

    private void updateProfile() {
        String name = Objects.requireNonNull(binding.formProfile.etName.getText()).toString().trim();
        String email = Objects.requireNonNull(binding.formProfile.etEmail.getText()).toString().trim();
        if (isFormValid()){
            userLoggedIn.setName(name);
            userLoggedIn.setEmail(email);
            viewModel.update(userLoggedIn).observe(this, isSuccess -> {
                Toast.makeText(this, R.string.update_profile_successed, Toast.LENGTH_SHORT).show();
            });
        }
    }

    private boolean isFormValid(){
        String name = Objects.requireNonNull(binding.formProfile.etName.getText()).toString().trim();
        String email = Objects.requireNonNull(binding.formProfile.etEmail.getText()).toString().trim();

        if (name.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, R.string.all_fields_are_required, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}