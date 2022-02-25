package com.example.notes;

import android.os.Parcel;
import android.os.Parcelable;

public class NoteInfo implements Parcelable {

    private CourseInfo mCourse;
    private  String mTitle;
    private String mText;

    public NoteInfo(CourseInfo mCourse, String mTitle, String mText) {
        this.mCourse = mCourse;
        this.mTitle = mTitle;
        this.mText = mText;
    }

    protected NoteInfo(Parcel in) {
        mCourse = in.readParcelable(CourseInfo.class.getClassLoader());
        mTitle = in.readString();
        mText = in.readString();
    }

    public static final Creator<NoteInfo> CREATOR = new Creator<NoteInfo>() {
        @Override
        public NoteInfo createFromParcel(Parcel in) {
            return new NoteInfo(in);
        }

        @Override
        public NoteInfo[] newArray(int size) {
            return new NoteInfo[size];
        }
    };

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(mCourse, i);
        parcel.writeString(mTitle);
        parcel.writeString(mText);
    }

    @Override
    public String toString() {
        return mCourse.getCourseId() + "|" + mTitle + "|" + mText;
    }
}
