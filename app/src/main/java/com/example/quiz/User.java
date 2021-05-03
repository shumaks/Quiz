package com.example.quiz;

public class User {
    private String name;
    private int rightAnswers;

    public User() {
    }

    public User(String name, int rightAnswers) {
        this.name = name;
        this.rightAnswers = rightAnswers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRightAnswers() {
        return rightAnswers;
    }

    public void setRightAnswers(int rightAnswers) {
        this.rightAnswers = rightAnswers;
    }


}
