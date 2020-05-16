package com.example.pictionary.model;

public class Picture {

    private int mId;
    private int mDifficulty;
    private String mUrl;
    private String mAnswer;
    private boolean mUsed;

    public Picture(int mId, int mDifficulty, String mUrl, String mAnswer) {
        this.mId = mId;
        this.mDifficulty = mDifficulty;
        this.mUrl = mUrl;
        this.mAnswer = mAnswer;
        this.mUsed = false;
    }

    public boolean isUsed() {
        return this.mUsed;
    }
    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public int getmDifficulty() {
        return mDifficulty;
    }

    public void setmDifficulty(int mDifficulty) {
        this.mDifficulty = mDifficulty;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public String getmAnswer() {
        return mAnswer;
    }

    public void setmAnswer(String mAnswer) {
        this.mAnswer = mAnswer;
    }
}
