package com.gmi.gtcm.db;

public class ImageQuiz {
    public static final String TABLE_NAME = "ImageQuiz";
    public static final String COLUMN_ID = "column_id";
    public static final String IMQUIZID = "quizid";
    public static final String IMQUESTIONS = "questions";
    public static final String IMPOSITION = "position";
    public static final String IMANSWER = "answer";
    public static final String IMDURATION = "duration";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + IMQUIZID + " TEXT," + IMQUESTIONS + " TEXT," + IMPOSITION + " TEXT," +IMANSWER +" TEXT,"+IMDURATION+" TEXT"+")";

private String imquizid;
private String imquestions;
private String imposition;
private String imanswer;
private String imduration;

    public ImageQuiz(String imquizid, String imquestions, String imposition, String imanswer, String imduration) {
        this.imquizid = imquizid;
        this.imquestions = imquestions;
        this.imposition = imposition;
        this.imanswer = imanswer;
        this.imduration = imduration;
    }

    public ImageQuiz() {
    }

    public String getImquizid() {
        return imquizid;
    }

    public void setImquizid(String imquizid) {
        this.imquizid = imquizid;
    }

    public String getImquestions() {
        return imquestions;
    }

    public void setImquestions(String imquestions) {
        this.imquestions = imquestions;
    }

    public String getImposition() {
        return imposition;
    }

    public void setImposition(String imposition) {
        this.imposition = imposition;
    }

    public String getImanswer() {
        return imanswer;
    }

    public void setImanswer(String imanswer) {
        this.imanswer = imanswer;
    }

    public String getImduration() {
        return imduration;
    }

    public void setImduration(String imduration) {
        this.imduration = imduration;
    }
}
