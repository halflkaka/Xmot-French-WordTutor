package com.example.shicanjie.xmot.Class;

/**
 * Created by shicanjie on 2017/12/24.
 */

public class AnswerQuestion {
    String Question;
    String CorrectAnswer;
    public AnswerQuestion(String Que, String Ans){
        Question = Que;
        CorrectAnswer = Ans;
    }
    public String GetQuestion(){return Question;}
    public String GetAnswer(){return CorrectAnswer;}
}
