package com.example.cars;

import android.app.Application;

public class MyApplication extends Application {
    public boolean CountdownStatus;

    public boolean getCountdownStatus(){
        return CountdownStatus;
    }

    public void setCountdownStatus(boolean isCountdownOn){
        CountdownStatus = isCountdownOn;
    }

}
