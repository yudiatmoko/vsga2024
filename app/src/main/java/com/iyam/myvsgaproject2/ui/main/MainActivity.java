/*
 *    Hi, Code Enthusiast!
 *    https://github.com/yudiatmoko
 */

/*
 *    Hi, Code Enthusiast!
 *    https://github.com/yudiatmoko
 */

package com.iyam.myvsgaproject2.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.splashscreen.SplashScreen;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.iyam.myvsgaproject2.R;
import com.iyam.myvsgaproject2.data.preferences.Preferences;
import com.iyam.myvsgaproject2.databinding.ActivityMainBinding;
import com.iyam.myvsgaproject2.ui.detail.DetailActivity;
import com.iyam.myvsgaproject2.ui.profile.ProfileActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FloatingActionButton fab;
    private RecyclerView rvNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(
                getLayoutInflater(),
                getWindow().getDecorView().findViewById(android.R.id.content),
                false
        );
        fab = binding.fab;
        rvNotes = binding.rvNotes;

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
        fab.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, DetailActivity.class)));
    }

    private void getUserPreferences() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.profile) {
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        }
        return true;
    }
}