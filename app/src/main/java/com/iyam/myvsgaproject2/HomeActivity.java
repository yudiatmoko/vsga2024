/*
 *    Hi, Code Enthusiast!
 *    https://github.com/yudiatmoko
 */

package com.iyam.myvsgaproject2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.iyam.myvsgaproject2.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    Button btnIntStorage, btnExtStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        btnIntStorage = binding.btnInternalStorage;
        btnExtStorage = binding.btnExternalStorage;

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
        btnIntStorage.setOnClickListener(view -> navigateToInternalPersistence());
        btnExtStorage.setOnClickListener(view -> navigateToExternalPersistence());
    }

    private void navigateToExternalPersistence() {
        Intent intent = new Intent(HomeActivity.this, ExternalStorageActivity.class);
        startActivity(intent);
    }

    private void navigateToInternalPersistence() {
        Intent intent = new Intent(HomeActivity.this, InternalStorageActivity.class);
        startActivity(intent);
    }
}