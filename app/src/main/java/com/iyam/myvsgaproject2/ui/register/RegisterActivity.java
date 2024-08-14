package com.iyam.myvsgaproject2.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.iyam.myvsgaproject2.R;
import com.iyam.myvsgaproject2.data.preferences.Preferences;
import com.iyam.myvsgaproject2.databinding.ActivityRegisterBinding;
import com.iyam.myvsgaproject2.model.User;
import com.iyam.myvsgaproject2.ui.login.LoginActivity;
import com.iyam.myvsgaproject2.ui.main.MainActivity;
import com.iyam.myvsgaproject2.ui.viewmodel.UserViewModel;
import com.iyam.myvsgaproject2.utils.TextExtension;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private TextView tvLogin;
    private Button btnRegister;
    private UserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        binding = ActivityRegisterBinding.inflate(
            getLayoutInflater(),
            getWindow().getDecorView().findViewById(android.R.id.content),
            false
        );
        tvLogin = binding.tvNavigateToLogin;
        btnRegister = binding.btnRegister;
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
    }

    private void setOnClickListener() {
        TextExtension.highLightWord(tvLogin, getString(R.string.login), () -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
        btnRegister.setOnClickListener(view -> doRegister());
    }

    private boolean isFormValid(){
        String name = Objects.requireNonNull(binding.formRegister.etName.getText()).toString().trim();
        String userEmail = Objects.requireNonNull(binding.formRegister.etEmail.getText()).toString().trim();
        String username = Objects.requireNonNull(binding.formRegister.etUsername.getText()).toString().trim();
        String userPassword = Objects.requireNonNull(binding.formRegister.etPassword.getText()).toString().trim();
        String userConfirmPassword = Objects.requireNonNull(binding.formRegister.etConfirmPassword.getText()).toString().trim();

        if (name.isEmpty() || userEmail.isEmpty() || username.isEmpty() || userPassword.isEmpty() || userConfirmPassword.isEmpty()) {
            Toast.makeText(this, R.string.all_fields_are_required, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!userPassword.equals(userConfirmPassword)) {
            Toast.makeText(this, R.string.passwords_do_not_match, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void doRegister() {
        String name = Objects.requireNonNull(binding.formRegister.etName.getText()).toString().trim();
        String userEmail = Objects.requireNonNull(binding.formRegister.etEmail.getText()).toString().trim();
        String username = Objects.requireNonNull(binding.formRegister.etUsername.getText()).toString().trim();
        String userPassword = Objects.requireNonNull(binding.formRegister.etPassword.getText()).toString().trim();
        if (isFormValid()) {
            viewModel.isUsernameExist(username).observe(RegisterActivity.this, isExist -> {
                if (isExist){
                    binding.formRegister.tilUsername.setError(getString(R.string.username_already_exists));
                } else {
                    viewModel.register(new User(null, name, username, userEmail, userPassword)
                    ).observe(RegisterActivity.this, isSuccess -> {
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        Preferences.setLoggedInUser(getBaseContext(), username);
                        Preferences.setLoggedInStatus(getBaseContext(), true);
                        finish();
                    });
                }
            });
        }
    }
}