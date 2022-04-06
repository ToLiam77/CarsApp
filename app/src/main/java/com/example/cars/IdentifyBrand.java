package com.example.cars;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Random;

public class IdentifyBrand extends AppCompatActivity{
    ImageView imgView;
    TextView validationMessage, correctBrandName;

    int CorrectAnswers, WrongAnswers;

    int counter = 10;
    CountDownTimer countdownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_brand);

        CarImageHelper imageHelper;
        imageHelper = new CarImageHelper();

        imgView = (ImageView) findViewById(R.id.CarImage);
        imageHelper.SetRandomCarImage(imgView);


        //Timer
        MyApplication myApplication = (MyApplication) this.getApplication();

        if (myApplication.getCountdownStatus()){
            startTimer();
        }

    }

    public void startTimer(){
        Button button = (Button)findViewById(R.id.btnSubmitIdentifyBrand);
        TextView timerText = (TextView) findViewById(R.id.timerText);
        timerText.setTextColor(Color.BLACK);

        counter = 10;

        countdownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerText.setText(String.valueOf(counter));
                counter--;
            }

            @Override
            public void onFinish() {
                timerText.setText("Time is Up!");
                timerText.setTextColor(Color.RED);
                btnClick(button);
                button.setText("Next");
            }
        }.start();
    }

    public void btnClick(View view){
        Button button = (Button)findViewById(R.id.btnSubmitIdentifyBrand);
        String btnText = button.getText().toString();
        TextView timerText = (TextView) findViewById(R.id.timerText);

        CarImageHelper imageHelper;
        imageHelper = new CarImageHelper();
        MyApplication myApplication = (MyApplication) this.getApplication();

        TextView txtCorrectAnswers = (TextView) findViewById(R.id.CorrectAnswers1);
        TextView txtWrongAnswers = (TextView) findViewById(R.id.WrongAnswers1);

        if (btnText.equals("Submit")){
            Spinner spinnerSelectBrand = (Spinner) findViewById(R.id.spinnerBrandName);
            String selectedBrand = spinnerSelectBrand.getSelectedItem().toString();

            validationMessage = (TextView) findViewById(R.id.validationText);
            correctBrandName = (TextView) findViewById(R.id.correctCarBrand);
            imgView = (ImageView) findViewById(R.id.CarImage);

            if (selectedBrand.equals(imageHelper.GetDisplayedBrand(imgView))){
                validationMessage.setText("CORRECT!");
                validationMessage.setTextColor(Color.rgb(00, 80, 00));
                CorrectAnswers++;
                txtCorrectAnswers.setText("Correct Answers: " + CorrectAnswers);
            }else{
                validationMessage.setText("WRONG!");
                validationMessage.setTextColor(Color.RED);
                correctBrandName.setText("Correct brand is " + imageHelper.GetDisplayedBrand((imgView)));
                correctBrandName.setTextColor(Color.BLUE);
                WrongAnswers++;
                txtWrongAnswers.setText("Wrong Answers: " + WrongAnswers);
            }
            button.setText("Next");
            if (myApplication.getCountdownStatus()){
                countdownTimer.cancel();
            }
        } else if (btnText.equals("Next")){
            validationMessage.setText(null);
            correctBrandName.setText(null);

            Drawable PreviousDisplayedImage = imgView.getDrawable();
            while(imgView.getDrawable().equals(PreviousDisplayedImage)){
                imageHelper.SetRandomCarImage(imgView);
            }
            button.setText("Submit");

            if (myApplication.getCountdownStatus()){
                counter = 10;
                timerText.setTextColor(Color.BLACK);
                countdownTimer.start();
            }
        }
    }

    public void returnHome(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putInt("correctAnswers", CorrectAnswers);
        outState.putInt("wrongAnswers", WrongAnswers);

        Button button = (Button)findViewById(R.id.btnSubmitIdentifyBrand);
        outState.putString("btnString", button.getText().toString());

        try{
            outState.putString("validationMessage", validationMessage.getText().toString());
        }catch (Exception e){

        }

        try{
            outState.putString("correctBrandName", correctBrandName.getText().toString());
        }catch (Exception e){

        }

        /*/Timer
        TextView timerText = (TextView) findViewById(R.id.timerText);
        try{
            outState.putString("timerText", timerText.getText().toString());
        }catch (Exception e){

        }

        outState.putInt("timerCounter", counter);

         */
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        CorrectAnswers = savedInstanceState.getInt("correctAnswers");
        WrongAnswers = savedInstanceState.getInt("wrongAnswers");

        TextView txtCorrectAnswers = (TextView) findViewById(R.id.CorrectAnswers1);
        TextView txtWrongAnswers = (TextView) findViewById(R.id.WrongAnswers1);

        txtCorrectAnswers.setText("Correct Answers: " + CorrectAnswers);
        txtWrongAnswers.setText("Wrong Answers: " + WrongAnswers);

        Button button = (Button)findViewById(R.id.btnSubmitIdentifyBrand);
        button.setText(savedInstanceState.getString("btnString"));

        //Validation
        validationMessage = (TextView) findViewById(R.id.validationText);
        correctBrandName = (TextView) findViewById(R.id.correctCarBrand);

        correctBrandName.setText(savedInstanceState.getString("correctBrandName"));
        correctBrandName.setTextColor(Color.BLUE);

        validationMessage.setText(savedInstanceState.getString("validationMessage"));
        if(validationMessage.getText().equals("CORRECT!")){
            validationMessage.setTextColor(Color.rgb(00, 80, 00));
        }else{
            validationMessage.setTextColor(Color.RED);
        }

        /*/Timer
        TextView timerText = (TextView) findViewById(R.id.timerText);
        timerText.setText(savedInstanceState.getString("timerText"));
        if (savedInstanceState.getString("timerText").equals("Time is Up!")){
            timerText.setTextColor(Color.RED);
        }

        counter = savedInstanceState.getInt("timerCounter");

         */
    }


}