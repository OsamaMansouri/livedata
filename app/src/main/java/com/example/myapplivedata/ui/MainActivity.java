package com.example.myapplivedata.ui;

import android.app.AlertDialog;
import android.os.Bundle;

import com.example.myapplivedata.R;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplivedata.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplivedata.data.Word;
import com.example.myapplivedata.ui.NewWordActivity;
import com.example.myapplivedata.ui.WordListAdapter;
import com.example.myapplivedata.viewmodel.WordViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private WordViewModel wordViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        WordListAdapter adapter = new WordListAdapter();
        adapter.setOnItemClickListener(word -> showUpdateDeleteDialog(word));

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        wordViewModel.getAllWords().observe(this, adapter::setWords);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewWordActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String word = data.getStringExtra(NewWordActivity.EXTRA_REPLY);
            if (word != null && !word.trim().isEmpty()) {
                wordViewModel.insert(new Word(word));
            }
        }
    }

    private void showUpdateDeleteDialog(Word word) {
        final EditText input = new EditText(this);
        input.setText(word.getWord());

        new AlertDialog.Builder(this)
                .setTitle("Update or Delete")
                .setView(input)
                .setPositiveButton("Update", (dialog, which) -> {
                    String newText = input.getText().toString().trim();
                    if (!newText.isEmpty()) {
                        word.setWord(newText);
                        wordViewModel.update(word);
                    }
                })
                .setNegativeButton("Delete", (dialog, which) -> {
                    wordViewModel.delete(word);
                })
                .setNeutralButton("Cancel", null)
                .show();
    }

}
