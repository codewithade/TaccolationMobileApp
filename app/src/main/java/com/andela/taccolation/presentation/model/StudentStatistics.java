package com.andela.taccolation.presentation.model;

import androidx.annotation.NonNull;

public class StudentStatistics {
    private final int attendance;
    private final int quiz;
    private final int test;
    private final int exam;
    private final int bonus;
    private final int rewards;
    private final int rating;

    public StudentStatistics() {
        this(0, 0, 0, 0, 0, 0, 0);
    }

    public StudentStatistics(int attendance, int quiz, int test, int exam, int bonus, int rewards, int rating) {
        this.attendance = attendance;
        this.quiz = quiz;
        this.test = test;
        this.exam = exam;
        this.bonus = bonus;
        this.rewards = rewards;
        this.rating = rating;
    }

    public int getAttendance() {
        return attendance;
    }

    public int getQuiz() {
        return quiz;
    }

    public int getTest() {
        return test;
    }

    public int getExam() {
        return exam;
    }

    public int getBonus() {
        return bonus;
    }

    public int getRewards() {
        return rewards;
    }

    public int getRating() {
        return rating;
    }

    @NonNull
    @Override
    public String toString() {
        return "StudentStatistics{" +
                "attendance=" + attendance +
                ", quiz=" + quiz +
                ", test=" + test +
                ", exam=" + exam +
                ", bonus=" + bonus +
                ", rewards=" + rewards +
                ", rating=" + rating +
                '}';
    }
}
