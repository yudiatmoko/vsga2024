package com.iyam.myvsgaproject2.ui.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.iyam.myvsgaproject2.data.preferences.Preferences;
import com.iyam.myvsgaproject2.databinding.ActivitySplashScreenBinding;
import com.iyam.myvsgaproject2.ui.login.LoginActivity;
import com.iyam.myvsgaproject2.ui.main.MainActivity;
import com.iyam.myvsgaproject2.ui.register.RegisterActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private ActivitySplashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        binding = ActivitySplashScreenBinding.inflate(
            getLayoutInflater(),
            getWindow().getDecorView().findViewById(android.R.id.content),
            false
        );

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getUserPreferences();
    }

    private void getUserPreferences() {
        boolean isLoggedIn = Preferences.getLoggedInStatus(this);
        if (isLoggedIn){
            navigateToMain();
        } else {
            navigateToLogin();
        }
    }

    private void navigateToLogin() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }, 2000);
    }

    private void navigateToMain() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }, 2000);
    }
}