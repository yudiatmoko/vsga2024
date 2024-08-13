package com.iyam.myvsgaproject2.ui.detail;

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
import androidx.lifecycle.ViewModelProvider;

import com.iyam.myvsgaproject2.R;
import com.iyam.myvsgaproject2.data.preferences.Preferences;
import com.iyam.myvsgaproject2.databinding.ActivityDetailBinding;
import com.iyam.myvsgaproject2.model.Note;
import com.iyam.myvsgaproject2.ui.viewmodel.NoteViewModel;
import com.iyam.myvsgaproject2.utils.TextExtension;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private Button btnSave, btnUpdate;
    private EditText etTitle, etContent;
    private NoteViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        binding = ActivityDetailBinding.inflate(
                getLayoutInflater(),
                getWindow().getDecorView().findViewById(android.R.id.content),
                false
        );
        btnSave = binding.btnSave;
        btnUpdate = binding.btnUpdate;
        etTitle = binding.etNoteTitle;
        etContent = binding.etContent;
        viewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setContentForEdit();
        setOnClickListener();
    }

    private void setContentForEdit() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.edit_notes);
            Note note = (Note) getIntent().getSerializableExtra("NOTE");
            etTitle.setText(note.getNoteTitle());
            etContent.setText(note.getNoteContent());
            btnSave.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
        }
    }

    private String getUsernamePref() {
        return Preferences.getLoggedInUser(this);
    }

    private void setOnClickListener() {
        btnSave.setOnClickListener(view -> insertNote());
        btnUpdate.setOnClickListener(view -> updateNote());
    }

    private void updateNote() {
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();
        if (isFormValid()){
            Note note = (Note) getIntent().getSerializableExtra(TextExtension.NOTE);
            note.setNoteTitle(title);
            note.setNoteContent(content);
            viewModel.updateNote(note).observe(DetailActivity.this, isSuccess -> {
                if (isSuccess){
                    finish();
                } else {
                    Toast.makeText(DetailActivity.this, R.string.update_note_failed, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private Boolean isFormValid(){
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();
        return !title.isEmpty() && !content.isEmpty();
    }

    private void insertNote() {
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();
        if(isFormValid()){
            viewModel.isTitleExist(title).observe(this, isExist -> {
                if (isExist){
                    Toast.makeText(DetailActivity.this, R.string.title_already_exist, Toast.LENGTH_SHORT).show();
                } else {
                    Note note = new Note(
                            null,
                            getUsernamePref(),
                            title,
                            content
                    );
                    viewModel.insertNote(note).observe(DetailActivity.this, isSuccess -> {
                        if (isSuccess){
                            finish();
                        } else {
                            Toast.makeText(DetailActivity.this, R.string.failed_to_insert_note, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        } else {
            Toast.makeText(this, R.string.all_fields_are_required, Toast.LENGTH_SHORT).show();
        }
    }
}