package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    private AdView mAdView;
    private DatabaseReference mDataBase;
    private final List<Question> quizList = new ArrayList<>();
    private final List<String> answersList = new ArrayList<>();
    private TextView textQuestion;
    private Button buttonAnswer1, buttonAnswer2, buttonAnswer3, buttonAnswer4, buttonNext, button50to50, buttonFriendHelp;
    private ProgressBar spinner;
    private ImageView imageView;
    private int questionNum = 0;
    private Context context;
    public static int rightAnswers = 0;
    public static int wrongAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        context = this.getApplicationContext();

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });

        textQuestion = findViewById(R.id.textQuestion);
        buttonAnswer1 = findViewById(R.id.buttonAnswer1);
        buttonAnswer2 = findViewById(R.id.buttonAnswer2);
        buttonAnswer3 = findViewById(R.id.buttonAnswer3);
        buttonAnswer4 = findViewById(R.id.buttonAnswer4);
        buttonNext = findViewById(R.id.buttonNext);
        button50to50 = findViewById(R.id.button50to50);
        buttonFriendHelp = findViewById(R.id.buttonFriendHelp);
        spinner = findViewById(R.id.progressBar);
        imageView = findViewById(R.id.imageView);
        mDataBase = FirebaseDatabase.getInstance().getReference("Question");
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
        buttonAnswer1.setVisibility(View.VISIBLE);
        buttonAnswer2.setVisibility(View.VISIBLE);
        buttonAnswer3.setVisibility(View.VISIBLE);
        buttonAnswer4.setVisibility(View.VISIBLE);
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
                rightAnswers++;
            } else {
                buttonAnswer1.setBackgroundColor(Color.parseColor("#FF0000"));
                wrongAnswers++;
            }
            buttonAnswer2.setClickable(false);
            buttonAnswer3.setClickable(false);
            buttonAnswer4.setClickable(false);
            buttonNext.setVisibility(View.VISIBLE);
        });

        buttonAnswer2.setOnClickListener(v -> {
            if(buttonAnswer2.getText().equals(currentQuestion.getRightAnswer())) {
                buttonAnswer2.setBackgroundColor(Color.parseColor("#008000"));
                rightAnswers++;
            } else {
                buttonAnswer2.setBackgroundColor(Color.parseColor("#FF0000"));
                wrongAnswers++;
            }
            buttonAnswer1.setClickable(false);
            buttonAnswer3.setClickable(false);
            buttonAnswer4.setClickable(false);
            buttonNext.setVisibility(View.VISIBLE);
        });

        buttonAnswer3.setOnClickListener(v -> {
            if(buttonAnswer3.getText().equals(currentQuestion.getRightAnswer())) {
                buttonAnswer3.setBackgroundColor(Color.parseColor("#008000"));
                rightAnswers++;
            } else {
                buttonAnswer3.setBackgroundColor(Color.parseColor("#FF0000"));
                wrongAnswers++;
            }
            buttonAnswer1.setClickable(false);
            buttonAnswer2.setClickable(false);
            buttonAnswer4.setClickable(false);
            buttonNext.setVisibility(View.VISIBLE);
        });

        buttonAnswer4.setOnClickListener(v -> {
            if(buttonAnswer4.getText().equals(currentQuestion.getRightAnswer())) {
                buttonAnswer4.setBackgroundColor(Color.parseColor("#008000"));
                rightAnswers++;
            } else {
                buttonAnswer4.setBackgroundColor(Color.parseColor("#FF0000"));
                wrongAnswers++;
            }
            buttonAnswer1.setClickable(false);
            buttonAnswer2.setClickable(false);
            buttonAnswer3.setClickable(false);
            buttonNext.setVisibility(View.VISIBLE);
        });

        buttonNext.setOnClickListener(v -> {
            if (++questionNum >= quizList.size()) {
                Intent intent = new Intent(context, EndActivity.class);
                startActivityForResult(intent, 1);
            } else {
                showQuestion(questionNum);
            }
        });

        button50to50.setOnClickListener(v -> {
            button50to50.setVisibility(View.INVISIBLE);
            List<Integer> nums = new ArrayList<>();
            if (!buttonAnswer1.getText().equals(currentQuestion.getRightAnswer())) {
                nums.add(1);
            }
            if (!buttonAnswer2.getText().equals(currentQuestion.getRightAnswer())) {
                nums.add(2);
            }
            if (!buttonAnswer3.getText().equals(currentQuestion.getRightAnswer())) {
                nums.add(3);
            }
            if (!buttonAnswer4.getText().equals(currentQuestion.getRightAnswer())) {
                nums.add(4);
            }
            System.out.println(nums);
            Collections.shuffle(nums);
            if (nums.get(0) == 1) {
                buttonAnswer1.setVisibility(View.INVISIBLE);
            }
            if (nums.get(0) == 2) {
                buttonAnswer2.setVisibility(View.INVISIBLE);
            }
            if (nums.get(0) == 3) {
                buttonAnswer3.setVisibility(View.INVISIBLE);
            }
            if (nums.get(0) == 4) {
                buttonAnswer4.setVisibility(View.INVISIBLE);
            }

            if (nums.get(1) == 1) {
                buttonAnswer1.setVisibility(View.INVISIBLE);
            }
            if (nums.get(1) == 2) {
                buttonAnswer2.setVisibility(View.INVISIBLE);
            }
            if (nums.get(1) == 3) {
                buttonAnswer3.setVisibility(View.INVISIBLE);
            }
            if (nums.get(1) == 4) {
                buttonAnswer4.setVisibility(View.INVISIBLE);
            }
        });


        buttonFriendHelp.setOnClickListener(v -> {
            buttonFriendHelp.setVisibility(View.INVISIBLE);
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Привет! Нужна помощь! Какой правильный ответ?\n" + currentQuestion.getQuestion() + "\n" +
                    "1. " + currentQuestion.getAnswer1() + "\n" +
                    "2. " + currentQuestion.getAnswer2() + "\n" +
                    "3. " + currentQuestion.getAnswer3() + "\n" +
                    "4. " + currentQuestion.getAnswer4());
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent,"Поделиться"));
        });

    }
}