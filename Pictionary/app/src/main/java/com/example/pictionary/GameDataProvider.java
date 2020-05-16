package com.example.pictionary;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;

import com.example.pictionary.model.Picture;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GameDataProvider {
    private ArrayList<Picture> mEasy1;
    private ArrayList<Picture> mEasy2;
    private ArrayList<Picture> mMedium;
    private ArrayList<Picture> mDiff1;
    private ArrayList<Picture> mDiff2;
    private String mJson;
    private int mCurrentState;
    private int mTries;
    private int mCorrect;
    private int mWrong;
    private CountDownTimer mTimer;
    CallbackInterface mCallback;

    public GameDataProvider(String json) {
        mCurrentState = 3;
        //mTimer = 0;
        mTries = 0;
        mJson = json;
        mEasy1 = new ArrayList<>();
        mEasy2 = new ArrayList<>();
        mMedium = new ArrayList<>();
        mDiff1 = new ArrayList<>();
        mDiff2 = new ArrayList<>();
    }
    public interface CallbackInterface {
        public void click();
    }

    public void setCallBack(CallbackInterface callback) {
        mCallback = callback;
    }

    public void loadData() {
        try {
            JSONObject obj = new JSONObject(mJson);
            Log.d("KRITI", "obj = " + obj);
            JSONArray array = (JSONArray) obj.get("array");

            JSONObject o;
            for(int i = 0; i < array.length(); i++) {
                o = array.getJSONObject(i);
                int diff = o.getInt("difficulty");
                Picture pic = new Picture(o.getInt("id"), o.getInt("difficulty"),
                        o.getString("imageUrl"), o.getString("answer"));
                if(diff == 1) {
                    mEasy1.add(pic);
                } else if(diff == 2) {
                    mEasy2.add(pic);
                } else if(diff == 3) {
                    mMedium.add(pic);
                } else if ( diff == 4) {
                    mDiff1.add(pic);
                } else if (diff == 5) {
                    mDiff2.add(pic);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Picture loadNext() {
        //mTries++;
        if(mTries == 5) {
            mCallback.click();
        }
        Picture picture = null;
        int index = -1;
        if(mCurrentState == 3) {
            while(true) {
                index = (int)Math.floor(Math.random() * 5);
                picture = mDiff1.get(index);
                if(!picture.isUsed()) {
                    break;
                }
            }
        } else if(mCurrentState == 1) {
            while(true) {
                index = (int)Math.floor(Math.random() * 5);
                picture = mEasy2.get(index);
                if(!picture.isUsed()) {
                    break;
                }
            }
        } else if(mCurrentState == 2) {
            while(true) {
                index = (int)Math.floor(Math.random() * 5);
                picture = mMedium.get(index);
                if(!picture.isUsed()) {
                    break;
                }
            }
        } else if(mCurrentState == 5) {
            while(true) {
                index = (int)Math.floor(Math.random() * 5);
                picture = mDiff2.get(index);
                if(!picture.isUsed()) {
                    break;
                }
            }
        } else if(mCurrentState == 4) {
            while(true) {
                index = (int)Math.floor(Math.random() * 5);
                picture = mDiff2.get(index);
                if(!picture.isUsed()) {
                    break;
                }
            }
        }


        return picture;
    }

    public void incrementState() {
        if(mCurrentState != 5) {
            mCurrentState++;
        }
        mTries++;
        if(mTries == 5) {
            mCallback.click();;
        }
    }

    public void decrementState() {
        if(mCurrentState != 1) {
            mCurrentState--;
        } else {
            mCallback.click();
        }
        mTries++;
        if(mTries == 5) {
            mCallback.click();;
        }
    }


}
