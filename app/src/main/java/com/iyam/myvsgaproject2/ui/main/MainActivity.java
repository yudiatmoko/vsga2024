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

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.iyam.myvsgaproject2.R;
import com.iyam.myvsgaproject2.data.preferences.Preferences;
import com.iyam.myvsgaproject2.databinding.ActivityMainBinding;
import com.iyam.myvsgaproject2.ui.detail.DetailActivity;
import com.iyam.myvsgaproject2.ui.main.note.NoteAdapter;
import com.iyam.myvsgaproject2.ui.profile.ProfileActivity;
import com.iyam.myvsgaproject2.ui.viewmodel.NoteViewModel;
import com.iyam.myvsgaproject2.utils.TextExtension;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FloatingActionButton fab;
    private RecyclerView rvNotes;
    private NoteAdapter noteAdapter;
    private NoteViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(
                getLayoutInflater(),
                getWindow().getDecorView().findViewById(android.R.id.content),
                false
        );
        fab = binding.fab;
        rvNotes = binding.rvNotes;
        viewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setNoteList();
        observeNotes();
        setOnClickListener();
    }

    private void observeNotes() {
        viewModel.noteList(Preferences.getLoggedInUser(this))
                .observe(this, noteList -> noteAdapter.setNotes(noteList));
    }

    private void setNoteList() {
        noteAdapter = new NoteAdapter();
        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        rvNotes.setAdapter(noteAdapter);
    }

    private void setOnClickListener() {
        fab.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, DetailActivity.class)));
        noteAdapter.setOnItemClickListener((NoteAdapter.OnDeleteItemClickListener) note ->
                viewModel.deleteNote(note).observe(MainActivity.this, isSuccess -> {
            if (isSuccess){
                observeNotes();
            }
        }));
        noteAdapter.setOnItemClickListener((NoteAdapter.OnEditItemClickListener) note -> {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(TextExtension.NOTE, note);
            startActivity(intent);
        });
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

    @Override
    protected void onResume() {
        super.onResume();
        observeNotes();
    }
}