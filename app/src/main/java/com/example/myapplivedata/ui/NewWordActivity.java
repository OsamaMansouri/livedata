package com.example.myapplivedata.ui;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplivedata.R;

public class NewWordActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "word";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);

        EditText editWordView = findViewById(R.id.edit_word);
        Button button = findViewById(R.id.button_save);

        button.setOnClickListener(view -> {
            String word = editWordView.getText().toString();
            Intent replyIntent = new Intent();
            replyIntent.putExtra(EXTRA_REPLY, word);
            setResult(RESULT_OK, replyIntent);
            finish();
        });
    }
}

