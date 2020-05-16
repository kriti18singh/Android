package com.example.pictionary;

import com.example.pictionary.model.Picture;

public class Game {
    private int mCurrentState;

    public Game(int state) {
        mCurrentState = state;
    }

   /* public Picture loadNext() {
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
    }*/
}
