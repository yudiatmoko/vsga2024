/*
 *    Hi, Code Enthusiast!
 *    https://github.com/yudiatmoko
 */

package com.iyam.myvsgaproject2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.iyam.myvsgaproject2.model.Contact;
import com.iyam.myvsgaproject2.data.DatabaseHelper;
import com.iyam.myvsgaproject2.R;
import com.iyam.myvsgaproject2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FloatingActionButton fab;
    ListView lvContact;
    ContactAdapter adapter;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        binding = ActivityMainBinding.inflate(
                getLayoutInflater(),
                getWindow().getDecorView().findViewById(android.R.id.content),
                false
        );
        fab = binding.floatingActionButton;
        lvContact = binding.listView;
        adapter = new ContactAdapter(this);
        dbHelper = new DatabaseHelper(this);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        lvContact.setAdapter(adapter);
        setOnClickListener();
    }

    private void setOnClickListener() {
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddNewActivity.class);
            startActivity(intent);
        });
        lvContact.setOnItemLongClickListener((adapterView, view, i, l) -> {
            showDeleteDialog(adapterView, i);
            return true;
        });
    }

    private void showDeleteDialog(AdapterView<?> adapterView, int item) {
        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(this);
        deleteDialog.setMessage(getString(R.string.message_confirm_delete));
        deleteDialog.setPositiveButton(R.string.action_yes, (dialogInterface, i) -> {
            deleteData(adapterView, item);
        });
        deleteDialog.setNegativeButton(R.string.action_cancel, null);
        AlertDialog alertDialog = deleteDialog.create();
        alertDialog.show();
    }

    private void deleteData(AdapterView<?> adapterView, int i) {
        Contact data = (Contact) adapterView.getAdapter().getItem(i);
        dbHelper.deleteById(data.name);
        adapter.clear();
        adapter.addAll(dbHelper.getData());
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.clear();
        adapter.addAll(dbHelper.getData());
    }
}