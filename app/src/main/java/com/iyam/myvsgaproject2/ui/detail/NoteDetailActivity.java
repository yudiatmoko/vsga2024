/*
 *    Hi, Code Enthusiast!
 *    https://github.com/yudiatmoko
 */

/*
 *    Hi, Code Enthusiast!
 *    https://github.com/yudiatmoko
 */

package com.iyam.myvsgaproject2.ui.detail;

import static com.iyam.myvsgaproject2.utils.Constants.NOTE_CONTENT;
import static com.iyam.myvsgaproject2.utils.Constants.NOTE_TITLE;

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

import com.iyam.myvsgaproject2.R;
import com.iyam.myvsgaproject2.databinding.ActivityNoteDetailBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class NoteDetailActivity extends AppCompatActivity {

    ActivityNoteDetailBinding binding;
    EditText etNoteTitle, etNoteContent;
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
        etNoteTitle = binding.etNoteTitle;
        etNoteContent = binding.etContent;

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setOnClickListener();
        setContentForUpdate();
    }

    private void isFormFilled() {
        String noteTitle = etNoteTitle.getText().toString().trim();
        String noteContent = etNoteContent.getText().toString();
        if (noteTitle.isEmpty()) {
            Toast.makeText(this, R.string.error_note_title_required, Toast.LENGTH_SHORT).show();
        } else if (noteContent.isEmpty()) {
            Toast.makeText(this, R.string.error_note_content_required, Toast.LENGTH_SHORT).show();
        } else {
            createNewNote(noteTitle, noteContent);
        }
    }

    private void setOnClickListener() {
        btnSave.setOnClickListener(view -> isFormFilled());
        btnUpdate.setOnClickListener(view -> updateFile());
    }

    private void createNewNote(String noteTitle, String noteContent) {
        String pathName = getFilesDir() + getString(R.string.dir_name);
        File directory = new File(pathName);
        if (!directory.exists()) {
            directory.mkdir();
        }
        File file = new File(pathName, noteTitle);

        if (file.exists()) {
            Toast.makeText(this, R.string.error_note_title_exists, Toast.LENGTH_SHORT).show();
        } else {
            FileOutputStream fos;
            try {
                file.createNewFile();
                fos = new FileOutputStream(file, false);
                fos.write(noteContent.getBytes());
                fos.flush();
                fos.close();
                Toast.makeText(this, R.string.message_note_saved, Toast.LENGTH_SHORT).show();
                finish();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setContentForUpdate() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.title_edit_note);
            etNoteTitle.setText(extras.getString(NOTE_TITLE));
            etNoteContent.setText(extras.getString(NOTE_CONTENT));
            btnSave.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
            etNoteTitle.setEnabled(false);
        }
    }

    private void updateFile() {
        String noteTitle = etNoteTitle.getText().toString().trim();
        String noteContent = etNoteContent.getText().toString();
        String pathName = getFilesDir() + getString(R.string.dir_name);
        File file = new File(pathName, noteTitle);

        if (noteContent.isEmpty()) {
            Toast.makeText(this, R.string.error_note_content_required, Toast.LENGTH_SHORT).show();
        } else {
            FileOutputStream outputStream;
            try {
                file.createNewFile();
                outputStream = new FileOutputStream(file, false);
                outputStream.write(noteContent.getBytes());
                outputStream.flush();
                outputStream.close();
                Toast.makeText(this, R.string.message_note_saved, Toast.LENGTH_SHORT).show();
                finish();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}