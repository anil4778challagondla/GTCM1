package com.gmi.gtcm.db;

/**
 * Created by ravi on 20/02/18.
 */

public class VoiceQuiz {
    public static final String TABLE_NAME = "pendingfile";
    public static final String COLUMN_ID = "column_id";
    public static final String SPELLINGBEEQUESTIONID = "SpellingBeeQuestionId";
    public static final String SPELLINGBEEQUIZID = "SpellingBeeQuizId";
    public static final String QUESTION = "Question";
    public static final String QUESTIONNUMBER = "QuestionNumber";
    public static final String HINT1 = "Hint1";
    public static final String HINT2 = "Hint2";
    public static final String HINT1USE = "Hint1use";
    public static final String HINT2USE = "Hint2use";
    public static final String CORRECT = "Correct";
    public static final String POINTS = "Points";

    private String SpellingBeeQuestionId;

    public VoiceQuiz(String spellingBeeQuestionId, String spellingBeeQuizId, String question, String questionNumber, String hint1, String hint2, String hint1use, String hint2use, String correct, String points) {
        SpellingBeeQuestionId = spellingBeeQuestionId;
        SpellingBeeQuizId = spellingBeeQuizId;
        Question = question;
        QuestionNumber = questionNumber;
        Hint1 = hint1;
        Hint2 = hint2;
        Hint1use = hint1use;
        Hint2use = hint2use;
        Correct = correct;
        Points = points;
    }

    private String SpellingBeeQuizId;
    private String Question;
    private String QuestionNumber;
    private String Hint1;
    private String Hint2;
    private String Hint1use;
    private String Hint2use;
    private String Correct;
    private String Points;
    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + SPELLINGBEEQUESTIONID + " TEXT," + SPELLINGBEEQUIZID + " TEXT," + QUESTION + " TEXT," + QUESTIONNUMBER + " TEXT," + HINT1
                    + " TEXT," + HINT2 + " TEXT," + HINT1USE + " TEXT," + HINT2USE + " TEXT," + CORRECT + " TEXT," + POINTS +")";

    public String getSpellingBeeQuestionId() {
        return SpellingBeeQuestionId;
    }

    public void setSpellingBeeQuestionId(String spellingBeeQuestionId) {
        SpellingBeeQuestionId = spellingBeeQuestionId;
    }

    public String getSpellingBeeQuizId() {
        return SpellingBeeQuizId;
    }

    public void setSpellingBeeQuizId(String spellingBeeQuizId) {
        SpellingBeeQuizId = spellingBeeQuizId;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getQuestionNumber() {
        return QuestionNumber;
    }

    public void setQuestionNumber(String questionNumber) {
        QuestionNumber = questionNumber;
    }

    public String getHint1() {
        return Hint1;
    }

    public void setHint1(String hint1) {
        Hint1 = hint1;
    }

    public String getHint2() {
        return Hint2;
    }

    public void setHint2(String hint2) {
        Hint2 = hint2;
    }

    public String getHint1use() {
        return Hint1use;
    }

    public void setHint1use(String hint1use) {
        Hint1use = hint1use;
    }

    public String getHint2use() {
        return Hint2use;
    }

    public void setHint2use(String hint2use) {
        Hint2use = hint2use;
    }

    public String getCorrect() {
        return Correct;
    }

    public void setCorrect(String correct) {
        Correct = correct;
    }

    public String getPoints() {
        return Points;
    }

    public void setPoints(String points) {
        Points = points;
    }

    public VoiceQuiz() {
    }



}
