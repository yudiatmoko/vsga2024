/*
 *    Hi, Code Enthusiast!
 *    https://github.com/yudiatmoko
 */

package com.iyam.myvsgaproject2.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iyam.myvsgaproject2.R;
import com.iyam.myvsgaproject2.data.database.entity.StudentEntity;
import com.iyam.myvsgaproject2.databinding.ActivityMainBinding;
import com.iyam.myvsgaproject2.ui.main.student.StudentAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    EditText etName, etIdNumber;
    Button btnSave, btnDelete;
    MainViewModel viewModel;
    RecyclerView rvStudent;
    StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        binding = ActivityMainBinding.inflate(
                getLayoutInflater(),
                getWindow().getDecorView().findViewById(android.R.id.content),
                false
        );
        etName = binding.etName;
        etIdNumber = binding.etId;
        btnSave = binding.btnSave;
        btnDelete = binding.btnDeleteData;
        rvStudent = binding.rvStudent;

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setRecyclerView();
        setOnClickListener();
        observeGetAllStudent();
    }

    private void setRecyclerView() {
        adapter = new StudentAdapter();
        rvStudent.setLayoutManager(new LinearLayoutManager(this));
        rvStudent.setAdapter(adapter);
    }

    private void observeGetAllStudent() {
        viewModel.getAllStudents().observe(this, studentEntities -> adapter.setStudents(studentEntities));
    }

    private void setOnClickListener() {
        btnSave.setOnClickListener(view -> {
            insertStudent();
            etName.getText().clear();
            etIdNumber.getText().clear();
        });
        btnDelete.setOnClickListener(view -> {
            deleteAll();
        });
        adapter.setOnItemClickListener(new StudentAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(StudentEntity student) {
                viewModel.deleteStudent(student);
            }
        });
    }

    private boolean isFormValid(){
        String studentName = etName.getText().toString().trim();
        String studentIdNumber = etIdNumber.getText().toString().trim();
        return !studentName.isEmpty() && !studentIdNumber.isEmpty();
    }

    private void insertStudent() {
        if(isFormValid()){
            String studentName = etName.getText().toString().trim();
            String studentIdNumber = etIdNumber.getText().toString().trim();
            viewModel.insertStudent(new StudentEntity(studentIdNumber, studentName));
            Toast.makeText(MainActivity.this, R.string.save_data_successfully, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.please_fill_all_required_fields, Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteAll() { viewModel.deleteAll(); }

}