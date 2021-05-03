package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button buttonStart;
    private EditText editTextName;
    public static User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonStart = findViewById(R.id.buttonStart);
        editTextName = findViewById(R.id.editTextName);
        Context context = this.getApplicationContext();
        buttonStart.setOnClickListener(v -> {
            if (!String.valueOf(editTextName.getText()).equals("")) {
                currentUser = new User(String.valueOf(editTextName.getText()), 0);
                Intent intent = new Intent(context, QuestionActivity.class);
                startActivityForResult(intent, 1);
            }
        }

        );
    }
}