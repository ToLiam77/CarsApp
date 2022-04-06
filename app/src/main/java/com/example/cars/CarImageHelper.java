package com.example.cars;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CarImageHelper {
    int CurrentBrand, CurrentCar;

    //2D array of brands and cars CarsList[CarBrand][CarType]
    int CarsList[][] = {{R.drawable.audi_1, R.drawable.audi_2, R.drawable.audi_3, R.drawable.audi_4, R.drawable.audi_5, R.drawable.audi_6, R.drawable.audi_7, R.drawable.audi_8, R.drawable.audi_9, R.drawable.audi_0},
            {R.drawable.bentley_1, R.drawable.bentley_2, R.drawable.bentley_3, R.drawable.bentley_4, R.drawable.bentley_5, R.drawable.bentley_6, R.drawable.bentley_7, R.drawable.bentley_8, R.drawable.bentley_9, R.drawable.bentley_0},
            {R.drawable.bmw_1, R.drawable.bmw_2, R.drawable.bmw_3, R.drawable.bmw_4, R.drawable.bmw_5, R.drawable.bmw_6, R.drawable.bmw_7, R.drawable.bmw_8, R.drawable.bmw_9, R.drawable.bmw_0},
            {R.drawable.fiat_1, R.drawable.fiat_2, R.drawable.fiat_3, R.drawable.fiat_4, R.drawable.fiat_5, R.drawable.fiat_6, R.drawable.fiat_7, R.drawable.fiat_8, R.drawable.fiat_9, R.drawable.fiat_0},
            {R.drawable.ford_1, R.drawable.ford_2, R.drawable.ford_3, R.drawable.ford_4, R.drawable.ford_5, R.drawable.ford_6, R.drawable.ford_7, R.drawable.ford_8, R.drawable.ford_9, R.drawable.ford_0},
            {R.drawable.honda_1, R.drawable.honda_2, R.drawable.honda_3, R.drawable.honda_4, R.drawable.honda_5, R.drawable.honda_6, R.drawable.honda_7, R.drawable.honda_8, R.drawable.honda_9, R.drawable.honda_0},
            {R.drawable.hyundai_1, R.drawable.hyundai_2, R.drawable.hyundai_3, R.drawable.hyundai_4, R.drawable.hyundai_5, R.drawable.hyundai_6, R.drawable.hyundai_7, R.drawable.hyundai_8, R.drawable.hyundai_9, R.drawable.hyundai_0},
            {R.drawable.jaguar_1, R.drawable.jaguar_2, R.drawable.jaguar_3, R.drawable.jaguar_4, R.drawable.jaguar_5, R.drawable.jaguar_6, R.drawable.jaguar_7, R.drawable.jaguar_8, R.drawable.jaguar_9, R.drawable.jaguar_0},
            {R.drawable.mercedes_benz_1, R.drawable.mercedes_benz_2, R.drawable.mercedes_benz_3, R.drawable.mercedes_benz_4, R.drawable.mercedes_benz_5, R.drawable.mercedes_benz_6, R.drawable.mercedes_benz_7, R.drawable.mercedes_benz_8, R.drawable.mercedes_benz_9, R.drawable.mercedes_benz_0},
            {R.drawable.toyota_1, R.drawable.toyota_2, R.drawable.toyota_3, R.drawable.toyota_4, R.drawable.toyota_5, R.drawable.toyota_6, R.drawable.toyota_7, R.drawable.toyota_8, R.drawable.toyota_9, R.drawable.toyota_0}
    };

    public void SetRandomCarImage(ImageView imgView){
        Random rand = new Random();
        CurrentBrand = rand.nextInt(10);
        CurrentCar = rand.nextInt(10);
        imgView.setImageResource(CarsList[CurrentBrand][CurrentCar]);
        imgView.setTag(CurrentBrand);
    }

    public void SetRandomCarImageExcludingBrands(ImageView imgView, List<String> ExcludedBrands){
        SetRandomCarImage(imgView);

        if(ExcludedBrands.contains(GetDisplayedBrand(imgView))){
            SetRandomCarImageExcludingBrands(imgView, ExcludedBrands);
        }
    }

    public String GetDisplayedBrand(ImageView imgView){
        Integer brandName = (Integer)imgView.getTag();
        switch(brandName) {
            case 0:
                return "Audi";
            case 1:
                return "Bentley";
            case 2:
                return "BMW";
            case 3:
                return "Fiat";
            case 4:
                return "Ford";
            case 5:
                return "Honda";
            case 6:
                return "Hyundai";
            case 7:
                return "Jaguar";
            case 8:
                return "Mercedes Benz";
            case 9:
                return "Toyota";
        }

        return null;
    }

    public void SetRandomCarImageFromBrand(ImageView imgView, String brandName){
        int BrandIndex = -1;

        switch(brandName.toLowerCase()) {
            case "audi":
                BrandIndex = 0;
                break;
            case "bentley":
                BrandIndex = 1;
                break;
            case "bmw":
                BrandIndex = 2;
                break;
            case "fiat":
                BrandIndex = 3;
                break;
            case "ford":
                BrandIndex = 4;
                break;
            case "honda":
                BrandIndex = 5;
                break;
            case "hyundai":
                BrandIndex = 6;
                break;
            case "jaguar":
                BrandIndex = 7;
                break;
            case "mercedes benz":
                BrandIndex = 8;
                break;
            case "toyota":
                BrandIndex = 9;
                break;
        }

        if (BrandIndex != -1){
            Random rand = new Random();
            CurrentBrand = BrandIndex;
            CurrentCar = rand.nextInt(10);
            imgView.setImageResource(CarsList[CurrentBrand][CurrentCar]);
        }

    }
}
