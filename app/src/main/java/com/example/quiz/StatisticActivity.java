package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class StatisticActivity extends AppCompatActivity {

    private SimpleAdapter usersAdapter;
    private ListView statisticList;
    private List<HashMap<String, String>> usersList;
    private List<User> sortedUsersList = new ArrayList<>();
    private DatabaseReference mDataBase;
    private Button buttonStartAgain;
    private HashMap<String, String> map;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        statisticList = findViewById(R.id.statisticList);
        buttonStartAgain = findViewById(R.id.buttonStartAgain);
        usersList = new ArrayList<>();
        usersAdapter = new SimpleAdapter(this, usersList, android.R.layout.simple_list_item_2, new String[]{"Name", "Statistic"}, new int[] {android.R.id.text1, android.R.id.text2});
        statisticList.setAdapter(usersAdapter);
        mDataBase = FirebaseDatabase.getInstance().getReference("User");
        Context context = this.getApplicationContext();
        getDataFromDB();
        buttonStartAgain.setOnClickListener(v -> {
            Intent intent = new Intent(context, MainActivity.class);
            startActivityForResult(intent, 1);
        });
    }

    private void getDataFromDB() {
        mDataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (usersList.size() > 0) {
                    usersList.clear();
                }
                for (DataSnapshot ds : snapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    sortedUsersList.add(user);
                }

                Collections.sort(sortedUsersList, new UserComparator());
                for (User s : sortedUsersList) {
                    map = new HashMap<>();
                    map.put("Name", s.getName());
                    map.put("Statistic", Integer.toString(s.getRightAnswers()));
                    usersList.add(map);
                }
                usersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Database error!");
            }
        });
    }
}