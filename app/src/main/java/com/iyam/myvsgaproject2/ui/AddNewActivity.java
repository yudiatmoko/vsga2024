/*
 *    Hi, Code Enthusiast!
 *    https://github.com/yudiatmoko
 */

/*
 *    Hi, Code Enthusiast!
 *    https://github.com/yudiatmoko
 */

package com.iyam.myvsgaproject2.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.iyam.myvsgaproject2.data.DatabaseHelper;
import com.iyam.myvsgaproject2.databinding.ActivityAddNewBinding;

public class AddNewActivity extends AppCompatActivity {

    ActivityAddNewBinding binding;
    Button btnSave, btnCancel;
    EditText etName, etAddress;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        binding = ActivityAddNewBinding.inflate(
                getLayoutInflater(),
                getWindow().getDecorView().findViewById(android.R.id.content),
                false
        );
        btnSave = binding.btnSubmit;
        btnCancel = binding.btnCancel;
        etName = binding.etName;
        etAddress = binding.etAddress;
        dbHelper = new DatabaseHelper(this);

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
        btnSave.setOnClickListener(view -> {
            String name = etName.getText().toString().trim();
            String address = etAddress.getText().toString().trim();
            dbHelper.addContact(name, address);
            finish();
        });
        btnCancel.setOnClickListener(view -> finish());
    }
}