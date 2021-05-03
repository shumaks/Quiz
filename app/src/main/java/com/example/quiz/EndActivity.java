package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EndActivity extends AppCompatActivity {

    private TextView textRight, textWrong;
    private Button buttonShowStatistic;
    private DatabaseReference mDataBase;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        textRight = findViewById(R.id.textRight);
        textWrong = findViewById(R.id.textWrong);
        buttonShowStatistic = findViewById(R.id.buttonShowStatistic);
        mDataBase = FirebaseDatabase.getInstance().getReference("User");

        MainActivity.currentUser.setRightAnswers(QuestionActivity.rightAnswers);
        mDataBase.push().setValue(MainActivity.currentUser);

        textRight.setText("Правильные ответы: " + QuestionActivity.rightAnswers);
        textWrong.setText("Неправильные ответы: " + QuestionActivity.wrongAnswers);


        Context context = this.getApplicationContext();
        buttonShowStatistic.setOnClickListener(v -> {
            Intent intent = new Intent(context, StatisticActivity.class);
            startActivityForResult(intent, 1);
        });
    }
}