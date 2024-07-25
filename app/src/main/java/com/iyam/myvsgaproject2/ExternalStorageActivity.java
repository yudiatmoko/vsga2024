package com.iyam.myvsgaproject2;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.iyam.myvsgaproject2.databinding.ActivityExternalStorageBinding;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class ExternalStorageActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityExternalStorageBinding binding;
    TextView textData;
    Button btnCreate, btnUpdate, btnRead, btnDelete;
    public static final String FILENAME = "data.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        binding = ActivityExternalStorageBinding.inflate(getLayoutInflater());
        btnCreate = binding.btnCreate;
        btnUpdate = binding.btnUpdate;
        btnRead = binding.btnRead;
        btnDelete = binding.btnDelete;
        textData = binding.tvData;

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

    void setOnClickListener() {
        btnCreate.setOnClickListener(this);
        btnRead.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    private void createFile(){
        String data = getString(R.string.this_is_data_from_createfile);
        String state = Environment.getExternalStorageState();

        if (!Environment.MEDIA_MOUNTED.equals(state)){
            return;
        }
        File file = new File(Environment.getExternalStorageDirectory(), FILENAME);
        FileOutputStream outputStream;
        try {
            file.createNewFile();
            outputStream = new FileOutputStream(file, true);
            outputStream.write(data.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateFile(){
        String data = getString(R.string.this_is_data_from_updatefile);
        String state = Environment.getExternalStorageState();

        if (!Environment.MEDIA_MOUNTED.equals(state)){
            return;
        }
        File file = new File(Environment.getExternalStorageDirectory(), FILENAME);
        FileOutputStream outputStream;
        try {
            file.createNewFile();
            outputStream = new FileOutputStream(file, false);
            outputStream.write(data.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFile(){
        File file = new File(Environment.getExternalStorageDirectory(), FILENAME);

        if(file.exists()){
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();
                while (line != null) {
                    text.append(line);
                    line = br.readLine();
                }
                br.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            textData.setText(text.toString());
        }
    }

    private void deleteFile(){
        File file = new File(Environment.getExternalStorageDirectory(), FILENAME);
        file.delete();
    }

    @Override
    public void onClick(View view) {
        runCommand(view.getId());
    }

    public void runCommand(int id){
        if (id == btnCreate.getId()) {
            createFile();
        } else if (id == btnUpdate.getId()) {
            updateFile();
        } else if (id == btnRead.getId()) {
            readFile();
        } else if (id == btnDelete.getId()) {
            deleteFile();
        }
    }
}