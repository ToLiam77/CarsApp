package com.example.cars;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class IdentifyCar extends AppCompatActivity {
    CarImageHelper imageHelper;

    ImageView img1, img2, img3;

    List<String> currentlyDisplayedBrands = new ArrayList<>();
    String CorrectBrand;
    Boolean isGameActive = true;
    int CorrectAnswers, WrongAnswers;

    int counter = 10;
    CountDownTimer countdownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_car);

        imageHelper = new CarImageHelper();

        //Set 3 Random Images of Different Brand Cars
        img1 = (ImageView) findViewById(R.id.CarImage1);
        imageHelper.SetRandomCarImage(img1);
        currentlyDisplayedBrands.add(imageHelper.GetDisplayedBrand(img1));

        img2 = (ImageView) findViewById(R.id.CarImage2);
        imageHelper.SetRandomCarImageExcludingBrands(img2, currentlyDisplayedBrands);
        currentlyDisplayedBrands.add(imageHelper.GetDisplayedBrand(img2));

        img3 = (ImageView) findViewById(R.id.CarImage3);
        imageHelper.SetRandomCarImageExcludingBrands(img3, currentlyDisplayedBrands);

        //Select one of the Images at Random and display its brand
        ImageView[] DisplayedImages = {img1, img2, img3};
        TextView RandomImageBrand = (TextView) findViewById(R.id.txtNameOfABrand);

        Random rand = new Random();
        String SelectedBrandToGuess = imageHelper.GetDisplayedBrand(DisplayedImages[rand.nextInt(DisplayedImages.length)]);
        RandomImageBrand.setText(SelectedBrandToGuess);
        CorrectBrand = SelectedBrandToGuess;

        //Timer
        MyApplication myApplication = (MyApplication) this.getApplication();

        if (myApplication.getCountdownStatus()){
            startTimer();
        }
    }

    public void isCorrectImage(View view){
        MyApplication myApplication = (MyApplication) this.getApplication();

        if (isGameActive){
            TextView VerificationText = (TextView) findViewById(R.id.VerificationText);
            TextView txtCorrectAnswers = (TextView) findViewById(R.id.CorrectAnswers);
            TextView txtWrongAnswers = (TextView) findViewById(R.id.WrongAnswers);
            ImageView SelectedImage = (ImageView) findViewById(view.getId());

            Button button = (Button)findViewById(R.id.btnDisplayedBrandName);

            if (imageHelper.GetDisplayedBrand(SelectedImage).equals(CorrectBrand)){
                VerificationText.setText("CORRECT!");
                VerificationText.setTextColor(Color.rgb(00, 80, 00));
                CorrectAnswers++;
                txtCorrectAnswers.setText("Correct Answers: " + CorrectAnswers);
            }else{
                VerificationText.setText("WRONG!");
                VerificationText.setTextColor(Color.RED);
                WrongAnswers++;
                txtWrongAnswers.setText("Wrong Answers: " + WrongAnswers);
            }

            button.setText("Next");
            isGameActive = false;

            if (myApplication.getCountdownStatus()){
                countdownTimer.cancel();
            }
        }

    }

    public void startTimer(){
        Button button = (Button)findViewById(R.id.btnDisplayedBrandName);
        TextView timerText = (TextView) findViewById(R.id.timerText1);
        TextView txtWrongAnswers = (TextView) findViewById(R.id.WrongAnswers);
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
                WrongAnswers++;
                txtWrongAnswers.setText("Wrong Answers: " + WrongAnswers);
                button.setText("Next");
            }
        }.start();
    }

    public void btnClick(View view){
        Button button = (Button)findViewById(R.id.btnDisplayedBrandName);

        TextView timerText = (TextView) findViewById(R.id.timerText1);
        MyApplication myApplication = (MyApplication) this.getApplication();

        if (button.getText().equals("Next")){

            Drawable[] DisplayedFiles = {img1.getDrawable(), img2.getDrawable(), img3.getDrawable()};

            img1 = (ImageView) findViewById(R.id.CarImage1);
            img2 = (ImageView) findViewById(R.id.CarImage2);
            img3 = (ImageView) findViewById(R.id.CarImage3);

            currentlyDisplayedBrands.clear();

            //While Any of the Currently Displayed Images Were Present last turn, get new images
            while (Arrays.asList(DisplayedFiles).contains(img1.getDrawable()) ||
                    Arrays.asList(DisplayedFiles).contains(img2.getDrawable()) ||
                    Arrays.asList(DisplayedFiles).contains(img3.getDrawable())){

                imageHelper.SetRandomCarImage(img1);
                currentlyDisplayedBrands.add(imageHelper.GetDisplayedBrand(img1));

                imageHelper.SetRandomCarImageExcludingBrands(img2, currentlyDisplayedBrands);
                currentlyDisplayedBrands.add(imageHelper.GetDisplayedBrand(img2));

                imageHelper.SetRandomCarImageExcludingBrands(img3, currentlyDisplayedBrands);
            }

            button.setText("Displayed Brand Name");

            //Select one of the Images at Random and display its brand
            ImageView[] DisplayedImages = {img1, img2, img3};
            TextView RandomImageBrand = (TextView) findViewById(R.id.txtNameOfABrand);

            Random rand = new Random();
            String SelectedBrandToGuess = imageHelper.GetDisplayedBrand(DisplayedImages[rand.nextInt(DisplayedImages.length)]);
            RandomImageBrand.setText(SelectedBrandToGuess);
            CorrectBrand = SelectedBrandToGuess;


            TextView VerificationText = (TextView) findViewById(R.id.VerificationText);
            VerificationText.setText("");
            isGameActive = true;

            //Timer
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

        //Validation
        TextView VerificationText = (TextView) findViewById(R.id.VerificationText);
        try{
            outState.putString("validationMessage", VerificationText.getText().toString());
        }catch (Exception e){

        }

        //Button
        Button button = (Button)findViewById(R.id.btnDisplayedBrandName);
        outState.putString("btnText", button.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        CorrectAnswers = savedInstanceState.getInt("correctAnswers");
        WrongAnswers = savedInstanceState.getInt("wrongAnswers");

        TextView txtCorrectAnswers = (TextView) findViewById(R.id.CorrectAnswers);
        TextView txtWrongAnswers = (TextView) findViewById(R.id.WrongAnswers);

        txtCorrectAnswers.setText("Correct Answers: " + CorrectAnswers);
        txtWrongAnswers.setText("Wrong Answers: " + WrongAnswers);

        //Validation
        TextView VerificationText = (TextView) findViewById(R.id.VerificationText);

        VerificationText.setText(savedInstanceState.getString("validationMessage"));
        if(VerificationText.getText().equals("CORRECT!")){
            VerificationText.setTextColor(Color.rgb(00, 80, 00));
        }else{
            VerificationText.setTextColor(Color.RED);
        }

        //Button
        Button button = (Button)findViewById(R.id.btnDisplayedBrandName);
        button.setText(savedInstanceState.getString("btnText"));

    }
}