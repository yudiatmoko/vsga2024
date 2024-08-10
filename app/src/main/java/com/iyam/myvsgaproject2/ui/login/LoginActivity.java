package com.iyam.myvsgaproject2.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.iyam.myvsgaproject2.R;
import com.iyam.myvsgaproject2.databinding.ActivityLoginBinding;
import com.iyam.myvsgaproject2.ui.register.RegisterActivity;
import com.iyam.myvsgaproject2.utils.TextExtension;

public class LoginActivity extends AppCompatActivity {

private ActivityLoginBinding binding;
    private TextView tvRegister;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        binding = ActivityLoginBinding.inflate(
            getLayoutInflater(),
            getWindow().getDecorView().findViewById(android.R.id.content),
            false
        );
        tvRegister = binding.tvNavigateToRegister;
        btnLogin = binding.btnRegister;

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
    }

    private void setForm() {
        binding.formRegister.tilName.setVisibility(View.GONE);
        binding.formRegister.tilPhone.setVisibility(View.GONE);
        binding.formRegister.tilConfirmPassword.setVisibility(View.GONE);
    }

    private void setOnClickListener() {
        TextExtension.highLightWord(tvRegister, getString(R.string.register), () -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });
    }
}