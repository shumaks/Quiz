package com.example.quiz;

import java.util.Comparator;

public class UserComparator implements Comparator<User> {
    public int compare(User o1, User o2) {
        int a1 = o1.getRightAnswers();
        int a2 = o2.getRightAnswers();

        if (a1 == a2)
            return 0;
        else if (a1 < a2)
            return 1;
        else
            return -1;
    }
}
