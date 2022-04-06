package com.example.cars;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyApplication myApplication = (MyApplication) this.getApplication();
        Switch timerSwitch = (Switch) findViewById(R.id.ToggleTimer);

        if (myApplication.getCountdownStatus()){
            timerSwitch.setChecked(true);
        }

        timerSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                myApplication.setCountdownStatus(true);
            }else{
                myApplication.setCountdownStatus(false);
            }
        });

    }

    public void clickIdentifyBrand(View view){
        Intent intent = new Intent(this, IdentifyBrand.class);
        startActivity(intent);
    }

    public void clickIdentifyCar(View view){
        Intent intent = new Intent(this, IdentifyCar.class);
        startActivity(intent);
    }

    public void clickSearchCarBrands(View view){
        Intent intent = new Intent(this, SearchCarBrands.class);
        startActivity(intent);
    }


}