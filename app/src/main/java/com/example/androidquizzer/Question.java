package com.example.androidquizzer;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class Question {

    private String value;
    private Answer answer;

    public Question(String value) {
        this.value = value;

    }

    public class Answer {

        private String value;

        private transient Question question;

        public Answer(String value) {
            this.value = value;
            this.question = Question.this;
            this.question.answer = this;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Question getQuestion() {
            if (question == null) {
                question = Question.this;
            }

            return question;
        }

        @Override
        public String toString() {
            return new GsonBuilder().setPrettyPrinting().create().toJson(this);
        }
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Question))
            return false;
        Question other = (Question) obj;

        return this.value.equals(other.value);
    }
    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
