package com.example.cars;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ipsec.ike.IkeSession;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Console;
import java.util.concurrent.TimeUnit;

public class SearchCarBrands extends AppCompatActivity{
    boolean isRotationActive = false;
    String requestedBrand;

    ImageView imageView;
    EditText nameOfBrand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_car_brands);

        imageView = (ImageView) findViewById(R.id.rotatingCarImage);
    }

    public void btnSubmitClick(View view) {
        view.setEnabled(false);

        nameOfBrand = (EditText) findViewById(R.id.NameOfABrand);
        requestedBrand = nameOfBrand.getText().toString();

        CarImageHelper imageHelper;
        imageHelper = new CarImageHelper();

        Handler handler = new Handler();

        isRotationActive = true;


        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                while (isRotationActive){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                imageHelper.SetRandomCarImageFromBrand(imageView, requestedBrand);
                                Drawable previousCarImage = imageView.getDrawable();
                                while (imageView.getDrawable().equals(previousCarImage)){
                                    imageHelper.SetRandomCarImageFromBrand(imageView, requestedBrand);
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });

                    synchronized (this) {
                        try {
                            for (int i = 0; i < 10; i++){
                                wait(300);
                                if (!isRotationActive){
                                    break;
                                }
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    public void btnStopClick(View view){
        Button submitBtn = (Button) findViewById(R.id.btnSubmit);
        submitBtn.setEnabled(true);

        isRotationActive = false;
        imageView.setImageDrawable(null);
    }



    public void returnHome(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}