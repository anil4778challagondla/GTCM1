package com.gmi.gtcm.db;

public class TextQuiz {
    public static final String TABLE_NAME = "TextQuiz";
    public static final String COLUMN_ID = "column_id";
    public static final String TQUIZID = "quizid";
    public static final String TQUESTIONS = "questions";
    public static final String TPOSITION = "position";
    public static final String TANSWER = "answer";
    public static final String TDURATION = "duration";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + TQUIZID + " TEXT," + TQUESTIONS + " TEXT," + TPOSITION + " TEXT," +TANSWER +" TEXT,"+TDURATION+" TEXT"+")";

    public TextQuiz(String tquizid, String tquestions, String tposition, String tanswer, String tduration) {
        this.tquizid = tquizid;
        this.tquestions = tquestions;
        this.tposition = tposition;
        this.tanswer = tanswer;
        this.tduration = tduration;
    }

    private String tquizid;
    private String tquestions;
    private String tposition;
    private String tanswer;
    private String tduration;

    public String getTquizid() {
        return tquizid;
    }

    public void setTquizid(String tquizid) {
        this.tquizid = tquizid;
    }

    public String getTquestions() {
        return tquestions;
    }

    public void setTquestions(String tquestions) {
        this.tquestions = tquestions;
    }

    public String getTposition() {
        return tposition;
    }

    public void setTposition(String tposition) {
        this.tposition = tposition;
    }

    public String getTanswer() {
        return tanswer;
    }

    public void setTanswer(String tanswer) {
        this.tanswer = tanswer;
    }

    public String getTduration() {
        return tduration;
    }

    public void setTduration(String tduration) {
        this.tduration = tduration;
    }

    public TextQuiz() {
    }
}
