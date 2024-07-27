package com.iyam.myvsgaproject2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.iyam.myvsgaproject2.databinding.ActivityNoteDetailBinding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class NoteDetailActivity extends AppCompatActivity {

    ActivityNoteDetailBinding binding;
    EditText etFileName, etNoteContent;
    Button btnSave, btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        binding = ActivityNoteDetailBinding.inflate(
                getLayoutInflater(),
                getWindow().getDecorView().findViewById(android.R.id.content),
                false
        );
        btnSave = binding.btnSave;
        btnUpdate = binding.btnUpdate;
        etFileName = binding.etFileName;
        etNoteContent = binding.etContent;

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setButtonVisibilty();
        setOnClickListener();
        checkUpdate();
    }

    private void setButtonVisibilty() {
        binding.btnUpdate.setVisibility(View.GONE);
        binding.btnSave.setVisibility(View.VISIBLE);
    }

    private void checkUpdate() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            getSupportActionBar().setTitle(R.string.ubah_catatan_text);
            etFileName.setText(extras.getString(MainActivity.CompanionObject.FILENAME));
            readFile();
            binding.btnSave.setVisibility(View.GONE);
            binding.btnUpdate.setVisibility(View.VISIBLE);
        }
    }

    private void setOnClickListener() {
        btnSave.setOnClickListener(view -> createOrUpdateFile(false));
        btnUpdate.setOnClickListener(view -> createOrUpdateFile(true));
    }

    private void createOrUpdateFile(boolean isUpdate) {
        String fileName = etFileName.getText().toString().trim();
        String data = etNoteContent.getText().toString();
        String path = getFilesDir().toString() + getString(R.string.dir_name);
        File file = new File(path, fileName);
        File parent = new File(path);

        if (!parent.exists()) {
            parent.mkdir();
        }

        if (isUpdate || (!file.exists() && parent.exists())) {
            saveFile(file, data);
        } else if (file.exists()) {
            Toast.makeText(this, R.string.file_name_already_exist_text, Toast.LENGTH_SHORT).show();
        }
    }

    private void saveFile(File file, String data) {
        FileOutputStream fos;
        try {
            file.createNewFile();
            fos = new FileOutputStream(file, true);
            fos.write(data.getBytes());
            fos.flush();
            fos.close();
            Toast.makeText(this, R.string.note_saved_text, Toast.LENGTH_SHORT).show();
            onBackPressed();
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void readFile() {
        String fileName = etFileName.getText().toString();
        String path = getFilesDir().toString() + getString(R.string.dir_name);
        File file = new File(path, fileName);

        if (file.exists()) {
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();
                while (line != null) {
                    text.append(line).append("\n");
                    line = br.readLine();
                }
                br.close();
            } catch (IOException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            etNoteContent.setText(text.toString());
        }
    }
}