/*
 *    Hi, Code Enthusiast!
 *    https://github.com/yudiatmoko
 */

/*
 *    Hi, Code Enthusiast!
 *    https://github.com/yudiatmoko
 */

package com.iyam.myvsgaproject2.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.splashscreen.SplashScreen;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.iyam.myvsgaproject2.R;
import com.iyam.myvsgaproject2.data.preferences.Preferences;
import com.iyam.myvsgaproject2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Button btnLogout;
    private TextView tvUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(
                getLayoutInflater(),
                getWindow().getDecorView().findViewById(android.R.id.content),
                false
        );
        btnLogout = binding.button;
        tvUsername = binding.tvHelloWorld;

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getUserPreferences();
        setOnClickListener();
    }

    private void setOnClickListener() {
        btnLogout.setOnClickListener(view -> {
            Preferences.clearLoggedInUser(getBaseContext());
            finish();
        });
    }

    private void getUserPreferences() {
        String username = Preferences.getLoggedInUser(this);
        tvUsername.setText(username);
    }
}