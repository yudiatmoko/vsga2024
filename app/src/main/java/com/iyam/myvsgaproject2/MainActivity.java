package com.iyam.myvsgaproject2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.iyam.myvsgaproject2.databinding.ActivityMainBinding;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ListView lvNotes;
    ArrayList<Map<String, Object>> dataList = new ArrayList<>();
    SimpleAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        binding = ActivityMainBinding.inflate(
                getLayoutInflater(),
                getWindow().getDecorView().findViewById(android.R.id.content),
                false
        );
        lvNotes = binding.lvNotes;

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getFiles();
        setOnClickListener();
        setAdapter();
    }

    private void setAdapter() {
        listAdapter = new SimpleAdapter(
                this,
                dataList,
                android.R.layout.simple_list_item_2,
                new String[]{CompanionObject.NAME, CompanionObject.DATE},
                new int[]{android.R.id.text1, android.R.id.text2}
        );
        lvNotes.setAdapter(listAdapter);
    }

    private void setOnClickListener() {
        lvNotes.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(MainActivity.this, NoteDetailActivity.class);
            Map<String, Object> data = (Map<String, Object>) adapterView.getAdapter().getItem(i);
            intent.putExtra(CompanionObject.FILENAME, data.get(CompanionObject.NAME).toString());
            startActivity(intent);
        });
        lvNotes.setOnItemLongClickListener((adapterView, view, i, l) -> {
            Map<String, Object> data = (Map<String, Object>) adapterView.getAdapter().getItem(i);
            showDeleteDialog(data.get(CompanionObject.NAME).toString());
            return true;
        });
    }

    private void showDeleteDialog(String fileName) {
        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(this);
        deleteDialog.setTitle(R.string.delete_file_title);
        deleteDialog.setMessage(getString(R.string.are_you_sure_to_delete_this_file, fileName));
        deleteDialog.setIcon(R.drawable.ic_delete);
        deleteDialog.setPositiveButton(R.string.yes_text, (dialogInterface, i) -> {
            deleteFileFunction(fileName);
        });
        deleteDialog.setNegativeButton(R.string.cancel_text, null);
        AlertDialog alertDialog = deleteDialog.create();
        alertDialog.show();
    }

    private void deleteFileFunction(String name){
        String path = getFilesDir().toString() + getString(R.string.dir_name);
        File file = new File(path, name);
        if (file.exists() && file.delete()) {
            getFiles();
            listAdapter.notifyDataSetChanged();
        }
    }

    private void getFiles() {
        String path = getFilesDir().toString() + getString(R.string.dir_name);
        File fileDirectory = new File(path);
        File[] files = fileDirectory.listFiles();
        dataList.clear();

        if (files != null && files.length > 0) {
            Arrays.sort(files, (f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified()));

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyy HH:mm");

            for (File file : files) {
                Date lastModifiedDate = new Date(file.lastModified());
                String fileName = file.getName();
                String fileDate = dateFormat.format(lastModifiedDate);
                Map<String, Object> listItemMap = new HashMap<>();
                listItemMap.put(CompanionObject.NAME, fileName);
                listItemMap.put(CompanionObject.DATE, fileDate);
                dataList.add(listItemMap);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_notes_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_notes_menu) {
            Intent intent = new Intent(this, NoteDetailActivity.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getFiles();
        listAdapter.notifyDataSetChanged();
    }

    static class CompanionObject {
        public static final String NAME = "name";
        public static final String DATE = "date";
        public static final String FILENAME = "filename";
    }
}