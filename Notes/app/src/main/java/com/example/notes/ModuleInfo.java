package com.example.notes;

import android.os.Parcel;
import android.os.Parcelable;

public class ModuleInfo implements Parcelable {

    private final String mModuleId;
    private final String mTitle;
    private boolean mIsComplete = false;

    public ModuleInfo(String moduleId, String title) {
        this(moduleId, title, false);
    }

    public ModuleInfo(String mModuleId, String mTitle, boolean mIsComplete) {
        this.mModuleId = mModuleId;
        this.mTitle = mTitle;
        this.mIsComplete = mIsComplete;
    }

    protected ModuleInfo(Parcel in) {
        mModuleId = in.readString();
        mTitle = in.readString();
        mIsComplete = in.readByte() != 0;
    }

    public static final Creator<ModuleInfo> CREATOR = new Creator<ModuleInfo>() {
        @Override
        public ModuleInfo createFromParcel(Parcel in) {
            return new ModuleInfo(in);
        }

        @Override
        public ModuleInfo[] newArray(int size) {
            return new ModuleInfo[size];
        }
    };

    public String getModuleId() {
        return mModuleId;
    }

    public String getTitle() {
        return mTitle;
    }

    public boolean isComplete() {
        return mIsComplete;
    }

    public void setComplete(boolean complete) {
        mIsComplete = complete;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mModuleId);
        parcel.writeString(mTitle);
        parcel.writeByte((byte) (mIsComplete ? 1 : 0));
    }
}
