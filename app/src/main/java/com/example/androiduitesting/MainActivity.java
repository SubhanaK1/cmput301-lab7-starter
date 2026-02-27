package com.example.androiduitesting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView cityList;
    private EditText newName;
    private LinearLayout nameField;

    private ArrayAdapter<String> cityAdapter;
    private ArrayList<String> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameField = findViewById(R.id.field_nameEntry);
        newName = findViewById(R.id.editText_name);
        cityList = findViewById(R.id.city_list);

        Button addButton = findViewById(R.id.button_add);
        Button clearButton = findViewById(R.id.button_clear);
        Button confirmButton = findViewById(R.id.button_confirm);

        dataList = new ArrayList<>();
        cityAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                dataList);

        cityList.setAdapter(cityAdapter);

        // ADD button
        addButton.setOnClickListener(v -> {
            nameField.setVisibility(View.VISIBLE);
        });

        // CONFIRM button
        confirmButton.setOnClickListener(v -> {
            String city = newName.getText().toString();
            dataList.add(city);
            cityAdapter.notifyDataSetChanged();
            newName.setText("");
            nameField.setVisibility(View.INVISIBLE);
        });

        // CLEAR button
        clearButton.setOnClickListener(v -> {
            dataList.clear();
            cityAdapter.notifyDataSetChanged();
        });

        // Click item → open ShowActivity
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            String selectedCity = dataList.get(position);
            Intent intent = new Intent(MainActivity.this, ShowActivity.class);
            intent.putExtra(ShowActivity.EXTRA_CITY_NAME, selectedCity);
            startActivity(intent);
        });
    }
}