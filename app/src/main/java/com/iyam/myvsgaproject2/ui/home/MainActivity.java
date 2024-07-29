/*
 *    Hi, Code Enthusiast!
 *    https://github.com/yudiatmoko
 */

package com.iyam.myvsgaproject2.ui.home;

import static com.iyam.myvsgaproject2.utils.Constants.NOTE_CONTENT;
import static com.iyam.myvsgaproject2.utils.Constants.NOTE_TITLE;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.iyam.myvsgaproject2.model.Note;
import com.iyam.myvsgaproject2.ui.detail.NoteAdapter;
import com.iyam.myvsgaproject2.ui.detail.NoteDetailActivity;
import com.iyam.myvsgaproject2.R;
import com.iyam.myvsgaproject2.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ListView lvNotes;
    NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        binding = ActivityMainBinding.inflate(
                getLayoutInflater(),
                getWindow().getDecorView().findViewById(android.R.id.content),
                false
        );
        lvNotes = binding.lvNotes;
        adapter = new NoteAdapter(this);
        lvNotes.setAdapter(adapter);

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

    private ArrayList<Note> getAllNotes() {
        ArrayList<Note> data = new ArrayList<>();

        String pathName = getFilesDir().toString() + getString(R.string.dir_name);
        File fileDirectory = new File(pathName);
        File[] files = fileDirectory.listFiles();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyy HH:mm");

        if (files != null) {
            Arrays.sort(files, (f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified()));
            for (File file : files) {
                String noteTitle = file.getName();
                Date date = new Date(file.lastModified());
                String timeStamp = dateFormat.format(date);
                data.add(new Note(noteTitle, timeStamp));
            }
        }
        return data;
    }

    private void setOnClickListener() {
        lvNotes.setOnItemClickListener((adapterView, view, i, l) -> {
            Note data = (Note) adapterView.getAdapter().getItem(i);
            startActivityFromUpdate(this, data.getNoteTitle(), readFile(data.getNoteTitle()));
        });
        lvNotes.setOnItemLongClickListener((adapterView, view, i, l) -> {
            Note data = (Note) adapterView.getAdapter().getItem(i);
            showDeleteDialog(data.getNoteTitle());
            return true;
        });
    }

    private void showDeleteDialog(String fileName) {
        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(this);
        deleteDialog.setTitle(R.string.title_delete_note);
        deleteDialog.setMessage(getString(R.string.message_confirm_delete, fileName));
        deleteDialog.setIcon(R.drawable.ic_delete);
        deleteDialog.setPositiveButton(R.string.action_yes, (dialogInterface, i) -> {
            deleteFileFunction(fileName);
        });
        deleteDialog.setNegativeButton(R.string.action_cancel, null);
        AlertDialog alertDialog = deleteDialog.create();
        alertDialog.show();
    }

    private void deleteFileFunction(String name) {
        String path = getFilesDir().toString() + getString(R.string.dir_name);
        File file = new File(path, name);
        if (file.exists() && file.delete()) {
            adapter.clear();
            adapter.addAll(getAllNotes());
        }
    }

    private String readFile(String noteTitle) {
        String pathName = getFilesDir().toString() + getString(R.string.dir_name);
        File file = new File(pathName, noteTitle);
        String content = "";

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
                content = text.toString();
                return text.toString();
            } catch (IOException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        return content;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_notes_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_notes_menu) {
            startActivity(this);
        }
        return true;
    }

    public void startActivity(Context context) {
        Intent intent = new Intent(context, NoteDetailActivity.class);
        startActivity(intent);
    }

    public void startActivityFromUpdate(Context context, String noteTitle, String noteContent) {
        Intent intent = new Intent(context, NoteDetailActivity.class);
        intent.putExtra(NOTE_TITLE, noteTitle);
        intent.putExtra(NOTE_CONTENT, noteContent);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.clear();
        adapter.addAll(getAllNotes());
    }
}