package com.tertandaid.openweather;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.kwabenaberko.openweathermaplib.model.currentweather.CurrentWeather;

import java.text.SimpleDateFormat;

public class WheaterUpdateDaily extends Worker {

    public WheaterUpdateDaily(

            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);

    }

    @Override
    public Result doWork() {

//        if (ContextCompat.checkSelfPermission(getApplicationContext(),
//                Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions((Activity) getApplicationContext(),
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                    MainActivity.LOCATION_PERMISSION_REQUEST_CODE);
//
//        } else {
//            MainActivity.getCurrentLocation();
//
//        }

        // Do the work here--in this case, upload the images.
        MainActivity.helperWheaterUpdateFunciton();

        // Indicate whether the work finished successfully with the Result
        return Result.success();
    }
}

