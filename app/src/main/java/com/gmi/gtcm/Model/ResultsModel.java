package com.gmi.gtcm.Model;

public class ResultsModel {


        private Integer quizResultId;
        private Integer quizId;
        private String answeredCount;

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    private String sno;
        private Integer correctAnswerCount;
        private String startTime;
        private String endTime;
        private String customerName;
        private String mobile;
        private String quizName;
        private Integer duration;
        private String durationString;
        private Integer totalRecords;
        private String rank;
        private Integer isSelf;

        public Integer getQuizResultId() {
            return quizResultId;
        }

        public void setQuizResultId(Integer quizResultId) {
            this.quizResultId = quizResultId;
        }

        public Integer getQuizId() {
            return quizId;
        }

        public void setQuizId(Integer quizId) {
            this.quizId = quizId;
        }

        public String getAnsweredCount() {
            return answeredCount;
        }

        public void setAnsweredCount(String answeredCount) {
            this.answeredCount = answeredCount;
        }

        public Integer getCorrectAnswerCount() {
            return correctAnswerCount;
        }

        public void setCorrectAnswerCount(Integer correctAnswerCount) {
            this.correctAnswerCount = correctAnswerCount;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getQuizName() {
            return quizName;
        }

        public void setQuizName(String quizName) {
            this.quizName = quizName;
        }

        public Integer getDuration() {
            return duration;
        }

        public void setDuration(Integer duration) {
            this.duration = duration;
        }

        public String getDurationString() {
            return durationString;
        }

        public void setDurationString(String durationString) {
            this.durationString = durationString;
        }

        public Integer getTotalRecords() {
            return totalRecords;
        }

        public void setTotalRecords(Integer totalRecords) {
            this.totalRecords = totalRecords;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public Integer getIsSelf() {
            return isSelf;
        }

        public void setIsSelf(Integer isSelf) {
            this.isSelf = isSelf;
        }

    }

