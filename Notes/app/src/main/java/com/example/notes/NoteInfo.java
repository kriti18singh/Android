package com.example.notes;

public class NoteInfo {

    private CourseInfo mCourse;
    private  String mTitle;
    private String mText;

    public NoteInfo(CourseInfo mCourse, String mTitle, String mText) {
        this.mCourse = mCourse;
        this.mTitle = mTitle;
        this.mText = mText;
    }

    public CourseInfo getCourse() {
        return mCourse;
    }

    public void setCourse(CourseInfo mCourse) {
        this.mCourse = mCourse;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getText() {
        return mText;
    }

    public void setText(String mText) {
        this.mText = mText;
    }


}
