package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.CollapsibleActionView;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    private DatabaseReference mDataBase;
    private String QUESTION_KEY = "Question";
    private List<Question> quizList = new ArrayList<>();
    private List<String> answersList = new ArrayList<>();
    private TextView textQuestion;
    private Button buttonAnswer1, buttonAnswer2, buttonAnswer3, buttonAnswer4, buttonNext;
    private ProgressBar spinner;
    private ImageView imageView;
    private int questionNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        textQuestion = findViewById(R.id.textQuestion);
        buttonAnswer1 = findViewById(R.id.buttonAnswer1);
        buttonAnswer2 = findViewById(R.id.buttonAnswer2);
        buttonAnswer3 = findViewById(R.id.buttonAnswer3);
        buttonAnswer4 = findViewById(R.id.buttonAnswer4);
        buttonNext = findViewById(R.id.buttonNext);
        spinner = findViewById(R.id.progressBar);
        imageView = findViewById(R.id.imageView);
        mDataBase = FirebaseDatabase.getInstance().getReference(QUESTION_KEY);
        getDataFromDB();
    }


    private void getDataFromDB() {
        mDataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (quizList.size() > 0) {
                    quizList.clear();
                }
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Question question = ds.getValue(Question.class);
                    quizList.add(question);
                }
                Collections.shuffle(quizList);
                showQuestion(questionNum);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Database error!");
            }
        });
    }

    private void showQuestion(int num) {
        buttonAnswer1.setBackgroundColor(Color.parseColor("#5065DD"));
        buttonAnswer2.setBackgroundColor(Color.parseColor("#5065DD"));
        buttonAnswer3.setBackgroundColor(Color.parseColor("#5065DD"));
        buttonAnswer4.setBackgroundColor(Color.parseColor("#5065DD"));
        buttonNext.setVisibility(View.INVISIBLE);
        Question currentQuestion = quizList.get(num);
        spinner.setVisibility(View.GONE);
        textQuestion.setText(currentQuestion.getQuestion());
        Glide
                .with(this)
                .load(currentQuestion.getImageURL())
                .into(imageView);
        answersList.clear();
        answersList.add(currentQuestion.getAnswer1());
        answersList.add(currentQuestion.getAnswer2());
        answersList.add(currentQuestion.getAnswer3());
        answersList.add(currentQuestion.getAnswer4());
        Collections.shuffle(answersList);

        buttonAnswer1.setText(answersList.get(0));
        buttonAnswer2.setText(answersList.get(1));
        buttonAnswer3.setText(answersList.get(2));
        buttonAnswer4.setText(answersList.get(3));

        buttonAnswer1.setOnClickListener(v -> {
            if(buttonAnswer1.getText().equals(currentQuestion.getRightAnswer())) {
                buttonAnswer1.setBackgroundColor(Color.parseColor("#008000"));
            } else {
                buttonAnswer1.setBackgroundColor(Color.parseColor("#FF0000"));
            }
            buttonAnswer2.setClickable(false);
            buttonAnswer3.setClickable(false);
            buttonAnswer4.setClickable(false);
            buttonNext.setVisibility(View.VISIBLE);
        });

        buttonAnswer2.setOnClickListener(v -> {
            if(buttonAnswer2.getText().equals(currentQuestion.getRightAnswer())) {
                buttonAnswer2.setBackgroundColor(Color.parseColor("#008000"));
            } else {
                buttonAnswer2.setBackgroundColor(Color.parseColor("#FF0000"));
            }
            buttonAnswer1.setClickable(false);
            buttonAnswer3.setClickable(false);
            buttonAnswer4.setClickable(false);
            buttonNext.setVisibility(View.VISIBLE);
        });

        buttonAnswer3.setOnClickListener(v -> {
            if(buttonAnswer3.getText().equals(currentQuestion.getRightAnswer())) {
                buttonAnswer3.setBackgroundColor(Color.parseColor("#008000"));
            } else {
                buttonAnswer3.setBackgroundColor(Color.parseColor("#FF0000"));
            }
            buttonAnswer1.setClickable(false);
            buttonAnswer2.setClickable(false);
            buttonAnswer4.setClickable(false);
            buttonNext.setVisibility(View.VISIBLE);
        });

        buttonAnswer4.setOnClickListener(v -> {
            if(buttonAnswer4.getText().equals(currentQuestion.getRightAnswer())) {
                buttonAnswer4.setBackgroundColor(Color.parseColor("#008000"));
            } else {
                buttonAnswer4.setBackgroundColor(Color.parseColor("#FF0000"));
            }
            buttonAnswer1.setClickable(false);
            buttonAnswer2.setClickable(false);
            buttonAnswer3.setClickable(false);
            buttonNext.setVisibility(View.VISIBLE);
        });

        buttonNext.setOnClickListener(v -> {
            if (++questionNum >= quizList.size()) {

            } else {
                showQuestion(questionNum);
            }
        });
    }
}