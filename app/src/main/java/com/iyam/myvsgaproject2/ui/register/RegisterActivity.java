package com.iyam.myvsgaproject2.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.iyam.myvsgaproject2.R;
import com.iyam.myvsgaproject2.databinding.ActivityRegisterBinding;
import com.iyam.myvsgaproject2.ui.login.LoginActivity;
import com.iyam.myvsgaproject2.utils.TextExtension;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private TextView tvLogin;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        binding = ActivityRegisterBinding.inflate(
            getLayoutInflater(),
            getWindow().getDecorView().findViewById(android.R.id.content),
            false
        );
        tvLogin = binding.tvNavigateToLogin;
        btnRegister = binding.btnRegister;

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
    }
}