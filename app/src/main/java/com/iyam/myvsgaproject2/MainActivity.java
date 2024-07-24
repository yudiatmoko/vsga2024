package com.iyam.myvsgaproject2;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.iyam.myvsgaproject2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ListView countryList;
    String[] name = new String[]{
            "Indonesia",
            "Malaysia",
            "Singapura",
            "Timor Leste",
            "Brunei Darussalam",
            "Thailand",
            "Vietnam",
            "Laos",
            "Kamboja",
            "Filipina",
            "China",
            "Mongolia",
            "Jepang",
            "Inggris",
            "Spanyol",
            "Italia",
            "Jerman",
            "Austria",
    };


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
        setListView();
        setOnClickListener();
    }

    private void setOnClickListener() {
        countryList.setOnItemClickListener((parent, view, position, id) ->
                Toast.makeText(MainActivity.this, getString(R.string.clicked, name[position]),
                Toast.LENGTH_LONG).show()
        );
    }

    private void setListView() {
        countryList = binding.lvCountryName;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                name
        );
        countryList.setAdapter(adapter);
    }
}