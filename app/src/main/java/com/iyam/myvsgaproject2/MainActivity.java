package com.iyam.myvsgaproject2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.iyam.myvsgaproject2.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    AppDatabase dbHelper;
    ArrayList<String> studentsData;
    EditText etName;
    Button btnSave, btnGet;
    TextView tvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        etName = binding.etName;
        btnSave = binding.btnSave;
        btnGet = binding.btnGetData;
        tvData = binding.tvStudentsNameData;
        dbHelper = new AppDatabase(this);

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
            String student = etName.getText().toString().trim();
            dbHelper.addStudent(student);
            etName.getText().clear();
            Toast.makeText(MainActivity.this, R.string.save_data_successfully, Toast.LENGTH_SHORT).show();
        });
        btnGet.setOnClickListener(view -> {
            studentsData = dbHelper.getAllStudentsList();
            etName.getText().clear();
            for (int i = 0; i < studentsData.size(); i++){
                tvData.setText(tvData.getText().toString() + ", " + studentsData.get(i));
            }
        });
    }
}