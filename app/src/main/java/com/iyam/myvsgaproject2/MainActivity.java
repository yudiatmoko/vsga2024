package com.iyam.myvsgaproject2;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.iyam.myvsgaproject2.databinding.ActivityMainBinding;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    EditText firstNumForm;
    EditText secondNumForm;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        firstNumForm = binding.etFirstNumber;
        secondNumForm = binding.etSecondNumber;
        result = binding.tvResult;
        setOnClickListener();
    }

    private void setOnClickListener() {
        binding.btnAddition.setOnClickListener(view -> operators(1));
        binding.btnSubtraction.setOnClickListener(view -> operators(2));
        binding.btnMultiplication.setOnClickListener(view -> operators(3));
        binding.btnDivision.setOnClickListener(view -> operators(4));
    }

    public void operators(int operation){
        if (!firstNumForm.getText().toString().isEmpty() && !secondNumForm.getText().toString().isEmpty()){
            int firstNumber = Integer.parseInt(firstNumForm.getText().toString());
            int secondNumber = Integer.parseInt(secondNumForm.getText().toString());
            int operationResult = 0;

            switch (operation){
                case 1:
                    operationResult = firstNumber + secondNumber;
                    result.setText(String.valueOf(operationResult));
                    break;
                case 2:
                    operationResult = firstNumber - secondNumber;
                    result.setText(String.valueOf(operationResult));
                    break;
                case 3:
                    operationResult = firstNumber * secondNumber;
                    result.setText(String.valueOf(operationResult));
                    break;
                case 4:
                    if (secondNumber != 0) {
                        float decimalResult = (float) (firstNumber) / (float) (secondNumber);
                        DecimalFormat df = new DecimalFormat("#.####");
                        result.setText(df.format(decimalResult));
                    } else {
                        result.setText(R.string.undefined);
                    }
                    break;
            }
        } else {
            Toast.makeText(this, getString(R.string.fill_form_required), Toast.LENGTH_SHORT).show();
        }
    }

    public void clearText(View view) {
        firstNumForm.getText().clear();
        secondNumForm.getText().clear();
        result.setText(R.string.zero);
    }
}