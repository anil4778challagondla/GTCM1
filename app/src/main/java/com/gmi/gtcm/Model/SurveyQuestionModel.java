package com.gmi.gtcm.Model;

public class SurveyQuestionModel {
    private String AnswerImagePath;

    public SurveyQuestionModel() {

    }

    public String getAnswerImagePath() {
        return AnswerImagePath;
    }

    public void setAnswerImagePath(String answerImagePath) {
        AnswerImagePath = answerImagePath;
    }

    public String getAnswerNumber() {
        return AnswerNumber;
    }

    public void setAnswerNumber(String answerNumber) {
        AnswerNumber = answerNumber;
    }

    public String getSmartQuizId() {
        return SmartQuizId;
    }

    public void setSmartQuizId(String smartQuizId) {
        SmartQuizId = smartQuizId;
    }

    public String getQuestionNumber() {
        return QuestionNumber;
    }

    public void setQuestionNumber(String questionNumber) {
        QuestionNumber = questionNumber;
    }

    public String getSmartQuizQuestionId() {
        return SmartQuizQuestionId;
    }

    public void setSmartQuizQuestionId(String smartQuizQuestionId) {
        SmartQuizQuestionId = smartQuizQuestionId;
    }

    public String getSmartQuizAnswerId() {
        return SmartQuizAnswerId;
    }

    public void setSmartQuizAnswerId(String smartQuizAnswerId) {
        SmartQuizAnswerId = smartQuizAnswerId;
    }



    private String AnswerNumber;
    private String SmartQuizId;
    private String QuestionNumber;
    private String SmartQuizQuestionId;
    private String SmartQuizAnswerId;

    private String SurveyquestionId,SurveyId,Question,QuestionNum,SurveyanswerId,Answer;



    public String getSurveyquestionId() {
        return SurveyquestionId;
    }

    public void setSurveyquestionId(String surveyquestionId) {
        SurveyquestionId = surveyquestionId;
    }

    public String getSurveyId() {
        return SurveyId;
    }

    public void setSurveyId(String surveyId) {
        SurveyId = surveyId;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getQuestionNum() {
        return QuestionNum;
    }

    public void setQuestionNum(String questionNum) {
        QuestionNum = questionNum;
    }

    public String getSurveyanswerId() {
        return SurveyanswerId;
    }

    public void setSurveyanswerId(String surveyanswerId) {
        SurveyanswerId = surveyanswerId;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public SurveyQuestionModel(String surveyquestionId, String surveyId, String question, String questionNum, String surveyanswerId, String answer,String answerImagePath, String answerNumber, String smartQuizId, String questionNumber, String smartQuizQuestionId, String smartQuizAnswerId) {
        SurveyquestionId = surveyquestionId;
        SurveyId = surveyId;
        Question = question;
        QuestionNum = questionNum;
        SurveyanswerId = surveyanswerId;
        Answer = answer;
        AnswerImagePath = answerImagePath;
        AnswerNumber = answerNumber;
        SmartQuizId = smartQuizId;
        QuestionNumber = questionNumber;
        SmartQuizQuestionId = smartQuizQuestionId;
        SmartQuizAnswerId = smartQuizAnswerId;
    }
}
