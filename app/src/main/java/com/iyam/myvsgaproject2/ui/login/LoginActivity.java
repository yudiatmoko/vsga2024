package com.iyam.myvsgaproject2.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.iyam.myvsgaproject2.databinding.ActivityLoginBinding;
import com.iyam.myvsgaproject2.ui.main.MainActivity;
import com.iyam.myvsgaproject2.ui.register.RegisterActivity;
import com.iyam.myvsgaproject2.ui.viewmodel.UserViewModel;
import com.iyam.myvsgaproject2.utils.TextExtension;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

private ActivityLoginBinding binding;
    private TextView tvRegister;
    private Button btnLogin;
    private UserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        binding = ActivityLoginBinding.inflate(
            getLayoutInflater(),
            getWindow().getDecorView().findViewById(android.R.id.content),
            false
        );
        tvRegister = binding.tvNavigateToRegister;
        btnLogin = binding.btnRegister;
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
        setForm();
        observeResult();
    }

    private void observeResult() {
        viewModel.getCurrentUser().observe(this, user1 -> {
            if (user1 != null){
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, R.string.account_not_found, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setForm() {
        binding.formRegister.tilName.setVisibility(View.GONE);
        binding.formRegister.tilEmail.setVisibility(View.GONE);
        binding.formRegister.tilConfirmPassword.setVisibility(View.GONE);
    }

    private void setOnClickListener() {
        TextExtension.highLightWord(tvRegister, getString(R.string.register), () -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });
        btnLogin.setOnClickListener(view -> doLogin());
    }

    private boolean isFormValid(){
        String username = Objects.requireNonNull(binding.formRegister.etUsername.getText()).toString().trim();
        String userPassword = Objects.requireNonNull(binding.formRegister.etPassword.getText()).toString().trim();

        if (username.isEmpty() || userPassword.isEmpty()) {
            Toast.makeText(this, R.string.all_fields_are_required, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void doLogin() {
        String username = Objects.requireNonNull(binding.formRegister.etUsername.getText()).toString().trim();
        String userPassword = Objects.requireNonNull(binding.formRegister.etPassword.getText()).toString().trim();
        if (isFormValid()) {
            viewModel.getUserByUsernamePassword(username, userPassword);
            Preferences.setLoggedInUser(this, username);
            Preferences.setLoggedInStatus(this, true);
        }
    }
}