package com.iyam.myvsgaproject2;

/*
 *    Hi, Code Enthusiast!
 *    https://github.com/yudiatmoko
 */

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iyam.myvsgaproject2.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    RecyclerView countryList;
    CountryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setRecyclerView();
    }

    private void setRecyclerView() {
        countryList = binding.rvCountryList;
        countryList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CountryAdapter(country -> {
            Toast.makeText(MainActivity.this, getString(R.string.clicked, country.getName()), Toast.LENGTH_SHORT).show();
        });
        countryList.setAdapter(adapter);

        List<Country> list = Country.getData();
        adapter.setData(list);
    }
}