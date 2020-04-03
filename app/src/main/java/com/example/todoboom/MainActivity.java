package com.example.todoboom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onCreateButtonClicked(View v){
        EditText inputText = findViewById(R.id.input_text);
        String value = inputText.getText().toString();
        inputText.getText().clear();
        TextView changingText = findViewById(R.id.chaging_text_view);
        changingText.setText(value);
    }
}
