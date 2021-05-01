package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity {

    private TextView textRight, textWrong;
    private Button buttonStartAgain;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        textRight = findViewById(R.id.textRight);
        textWrong = findViewById(R.id.textWrong);
        buttonStartAgain = findViewById(R.id.buttonStartAgain);
        textRight.setText("Правильные ответы: " + QuestionActivity.rightAnswers);
        textWrong.setText("Неправильные ответы: " + QuestionActivity.wrongAnswers);
        Context context = this.getApplicationContext();
        buttonStartAgain.setOnClickListener(v -> {
            Intent intent = new Intent(context, QuestionActivity.class);
            startActivityForResult(intent, 1);
        });
    }
}