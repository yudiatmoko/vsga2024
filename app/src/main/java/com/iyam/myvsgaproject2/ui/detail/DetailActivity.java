package com.iyam.myvsgaproject2.ui.detail;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
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

import java.time.LocalDateTime;
import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private Button btnSave, btnUpdate;
    private EditText etTitle, etContent;
    private NoteViewModel viewModel;
    private String oldContent = "";
    private String oldTitle = "";

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

        Typeface customFont = ResourcesCompat.getFont(this, R.font.nunito_sans_medium);
        etTitle.setTypeface(customFont);
        etContent.setTypeface(customFont);

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
            Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.detail_notes);
            Note note = (Note) getIntent().getSerializableExtra(TextExtension.NOTE);
            etTitle.setText(note.getNoteTitle());
            etContent.setText(note.getNoteContent());
            oldContent = note.getNoteContent();
            oldTitle = note.getNoteTitle();
            btnSave.setVisibility(View.GONE);

            etTitle.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    String currentTitle = charSequence.toString();
                    if (currentTitle.equalsIgnoreCase(oldTitle)){
                        btnUpdate.setVisibility(View.GONE);
                    } else {
                        btnUpdate.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            etContent.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    String currentContent = charSequence.toString();
                    if (currentContent.equalsIgnoreCase(oldContent)) {
                        btnUpdate.setVisibility(View.GONE);
                    } else {
                        btnUpdate.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
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
            note.setDate(LocalDateTime.now());
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
            Note note = new Note(
                    null,
                    getUsernamePref(),
                    title,
                    content,
                    LocalDateTime.now()
            );
            viewModel.insertNote(note).observe(DetailActivity.this, isSuccess -> {
                if (isSuccess){
                    finish();
                } else {
                    Toast.makeText(DetailActivity.this, R.string.failed_to_insert_note, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, R.string.all_fields_are_required, Toast.LENGTH_SHORT).show();
        }
    }
}