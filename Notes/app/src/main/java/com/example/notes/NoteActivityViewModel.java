package com.example.notes;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

public class NoteActivityViewModel extends ViewModel {
    public static final String ORIGINAL_NOTE_COURSE_ID = "com.example.notes.ORIGINAL_NOTE_COURSE_ID";
    public static final String ORIGINAL_NOTE_TEXT = "com.example.notes.ORIGINAL_NOTE_TEXT";
    public static final String ORIGINAL_NOTE_TITLE = "com.example.notes.ORIGINAL_NOTE_TITLE";

    public String mOriginalNoteTitle;
    public String mOriginalNoteText;
    public String mOriginalNoteCourseId;

    public boolean mIsNewlyCreated = true;

    public void saveState(Bundle outState) {
        outState.putString(ORIGINAL_NOTE_COURSE_ID, mOriginalNoteCourseId);
        outState.putString(ORIGINAL_NOTE_TITLE, mOriginalNoteTitle);
        outState.putString(ORIGINAL_NOTE_TEXT, mOriginalNoteText);
    }

    public void restoreState(Bundle inState) {
        mOriginalNoteCourseId = inState.getString(ORIGINAL_NOTE_COURSE_ID);
        mOriginalNoteTitle = inState.getString(ORIGINAL_NOTE_TITLE);
        mOriginalNoteText = inState.getString(ORIGINAL_NOTE_TEXT);
    }
}
