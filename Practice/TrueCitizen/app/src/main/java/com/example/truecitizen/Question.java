package com.example.truecitizen;

public class Question {
    private int questionResId;
    private boolean answer;

    public Question(int questionResId, boolean answer) {
        this.questionResId = questionResId;
        this.answer = answer;
    }

    public int getQuestionResId() {
        return questionResId;
    }

    public boolean isAnswer() {
        return answer;
    }
}
