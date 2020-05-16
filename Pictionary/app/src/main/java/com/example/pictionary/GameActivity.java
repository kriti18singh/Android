package com.example.pictionary;

import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pictionary.model.Picture;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GameActivity extends AppCompatActivity implements GameDataProvider.CallbackInterface {

    GameDataProvider mProvider;
    ImageView mImageView;
    Button mSubmit;
    EditText mEditText;
    Picture mPicture;
    TextView mTimerView;
    private CountDownTimer mTimer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mImageView = findViewById(R.id.image);
        mEditText = findViewById(R.id.answer);
        mTimerView = findViewById(R.id.timer);

        mSubmit = findViewById(R.id.submit);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEditText.getText() == null || mEditText.getText().toString().isEmpty()) {
                    Toast.makeText(GameActivity.this, "Please enter an answer", Toast.LENGTH_LONG).show();
                    return;
                }
                String answer = mEditText.getText().toString();
                if(answer.equalsIgnoreCase(mPicture.getmAnswer())) {
                    mProvider.incrementState();
                } else {
                    mProvider.decrementState();
                }
                mEditText.setText("");
                showQuestion();
            }
        });

        String s = readAssets();
        mProvider = new GameDataProvider(s);
        mProvider.setCallBack(this);
        mProvider.loadData();
        showQuestion();
        /*mTimer = new CountDownTimer(30000, 0) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimerView.setText("" +millisUntilFinished);
            }

            @Override
            public void onFinish() {
                showQuestion();
            }
        }.start();*/
    }

    private void showQuestion() {
        mPicture = mProvider.loadNext();
        //mImageView.setImageURI();

        Glide.with(this).load(Uri.parse(mPicture.getmUrl())).into(mImageView);
    }

    private String readAssets() {
        StringBuffer sb = new StringBuffer();
        try {
            InputStream is = this.getAssets().open("pic.json");
            int size = is.available();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line;

            while((line = reader.readLine()) != null) {
                sb.append(line);
            }
            Log.d("KRITI", "sb " + sb.toString());



        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public void click() {
        Toast.makeText(this, "Game over", Toast.LENGTH_LONG).show();
    }
}
