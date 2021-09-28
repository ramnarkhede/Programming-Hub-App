package com.itsram.quizapp;

public class TheoryModel {
    String filename,fileurl;
    public TheoryModel(){

    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    public TheoryModel(String filename, String fileurl) {
        this.filename = filename;
        this.fileurl = fileurl;
    }
}
