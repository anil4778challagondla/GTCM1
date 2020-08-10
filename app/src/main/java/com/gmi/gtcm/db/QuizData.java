package com.gmi.gtcm.db;

public class QuizData {

    public static final String TABLE_NAME = "QuizData";
    public static final String COLUMN_ID = "column_id";
    public static final String SPELLINGBEEQUIZID = "SpellingBeeQuizId";
    public static final String QUESTIONS = "Questions";
    public static final String POSITION = "pos";
    public static final String ANSWERS = "Answers";
    public static final String TIME = "time";
    public static final String POINTS = "points";
    public static final String ANSWEREDCOUNT = "answeredcount";
    public static final String CORRECTANSWEREDCOUNT = "correctansweredcount";


    public QuizData(String spellingbeeQuizId, String questions, String position, String answers, String time, String points,String answeredcount,String correctansweredcount) {
        SpellingbeeQuizId = spellingbeeQuizId;
        Questions = questions;
        Position = position;
        Answers = answers;
        Time = time;
        Points = points;
        Answeredcount = answeredcount;
        Correctansweredcount = correctansweredcount;
    }

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + SPELLINGBEEQUIZID + " TEXT," + QUESTIONS + " TEXT," + POSITION + " TEXT," + ANSWERS + " TEXT," +TIME +" TEXT,"+POINTS+" TEXT,"+ANSWEREDCOUNT+" TEXT,"+CORRECTANSWEREDCOUNT+" TEXT"+")";


    public QuizData() {
    }
    private String SpellingbeeQuizId;
    private String Answeredcount;

    public String getAnsweredcount() {
        return Answeredcount;
    }

    public void setAnsweredcount(String answeredcount) {
        Answeredcount = answeredcount;
    }

    public String getCorrectansweredcount() {
        return Correctansweredcount;
    }

    public void setCorrectansweredcount(String correctansweredcount) {
        Correctansweredcount = correctansweredcount;
    }

    private String Correctansweredcount;

    public String getSpellingbeeQuizId() {
        return SpellingbeeQuizId;
    }

    public void setSpellingbeeQuizId(String spellingbeeQuestionId) {
        SpellingbeeQuizId = spellingbeeQuestionId;
    }

    public String getQuestions() {
        return Questions;
    }

    public void setQuestions(String questions) {
        Questions = questions;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public String getAnswers() {
        return Answers;
    }

    public void setAnswers(String answers) {
        Answers = answers;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getPoints() {
        return Points;
    }

    public void setPoints(String points) {
        Points = points;
    }

    private String Questions;
    private String Position;
    private String Answers;
    private String Time;
    private String Points;

}
