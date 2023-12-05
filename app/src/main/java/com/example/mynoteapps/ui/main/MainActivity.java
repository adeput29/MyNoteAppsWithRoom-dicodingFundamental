package com.example.mynoteapps.ui.main;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.mynoteapps.R;
import com.example.mynoteapps.databinding.ActivityMainBinding;
import com.example.mynoteapps.helper.ViewModelFactory;
import com.example.mynoteapps.ui.insert.NoteAddUpdateActivity;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainViewModel mainViewModel = obtainViewModel(MainActivity.this);
        mainViewModel.getAllNotes().observe(this, notes -> {
            if (notes != null) {
                adapter.setListNotes(notes);
            }
        });
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        adapter = new NoteAdapter();
        binding.rvNotes.setLayoutManager(new LinearLayoutManager(this));
        binding.rvNotes.setHasFixedSize(true);
        binding.rvNotes.setAdapter(adapter);

        binding.fabAdd.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NoteAddUpdateActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
    @NonNull
    private static MainViewModel obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, factory).get(MainViewModel.class);
    }
}