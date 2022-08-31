package com.tertandaid.openweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.kwabenaberko.openweathermaplib.constant.Languages;
import com.kwabenaberko.openweathermaplib.constant.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.implementation.callback.CurrentWeatherCallback;
import com.kwabenaberko.openweathermaplib.implementation.callback.ThreeHourForecastCallback;
import com.kwabenaberko.openweathermaplib.model.currentweather.CurrentWeather;
import com.kwabenaberko.openweathermaplib.model.threehourforecast.ThreeHourForecast;
import com.squareup.picasso.Picasso;
import com.tertandaid.openweather.Location.FetchAddressIntentServices;
import com.tertandaid.openweather.Model.Threeday.ThreeDayList;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "main-activity" ;
    private static String district;
    private static String state;

    private static String city2;
    private static String state2;
    private static String address;

    Button buttonInfoWeather, buttonInfoWeatherHourly ,buttonInfoWeather3;
    public static OpenWeatherMapHelper helper;

    public static String dateTime, timeAja;

    static ResultReceiver resultReceiver;
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    static TextView
            LocationTV, CoordinateTV,
            jamTV, tanggalTV, WeatherDescriptionTV, TemperatureFeelsLikeTV,
            TemperatureTV, WindSpeedTV, CityCountryTV, MaxTempTV,
            todayIcon, todayPressure, MinTempTV, CountryTV,
            todayUVIndex, todayHumidity, todaySunrise, todaySunset;
    static ImageView imageAwan;

    private static Context context;

    RecyclerView Weather24RV;
//    HorizontalScrollView horizontalScrollView;
    ArrayList<DataModelMainActivity> data;

    AdapterMainActivity mAdapter ;
    SwipeRefreshLayout swipeContainer;

    String[] temperatureArray = {
            "30,\u200B2o C",
            "32,\u200B2o C",
            "40,\u200B2o C",
            "32,\u200B2o C"
    };

    int[] imageArray = {
            R.drawable.rain,
            R.drawable.sunny,
            R.drawable.thunderstorm,
            R.drawable.thunderstorm
    };

    String[] statusArray = {
            "online",
            "online",
            "online",
            "Not online"
    };

    String[] jamArray = {
            "10:00 ",
            "11:00 ",
            "12:00 ",
            "13:00 "
    };
//    static ProgressDialog progressDialog;

//    private Weather todayWeather = new Weather();
//    String todayWeatherfun;
//    DateFormat timeFormat;
    public static final String IMG_URL = "https://openweathermap.org/img/w/";
    static String locale;
    public static double lati, longi;

    private List<ThreeDayList> listMainThreeDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.context = getApplicationContext();

        Typeface typeface = ResourcesCompat.getFont(this, R.font.sourcesansproregular);
        Typeface typefaceBold = ResourcesCompat.getFont(this, R.font.sourcesansprobold);

        locale = getResources().getConfiguration().locale.getCountry();

        resultReceiver = new AddressResultReceiver(new Handler());

        buttonInfoWeather = findViewById(R.id.buttonInfoWeather);
        buttonInfoWeatherHourly = findViewById(R.id.buttonInfoWeatherHourly);
        buttonInfoWeather3 = findViewById(R.id.buttonInfoWeather3);
        tanggalTV = findViewById(R.id.tanggalTV);
        tanggalTV.setTypeface(typeface);
        LocationTV = findViewById(R.id.LocationTV);
        LocationTV.setTypeface(typefaceBold);
        CoordinateTV = findViewById(R.id.CoordinateTV);
        WeatherDescriptionTV = findViewById(R.id.WeatherDescriptionTV);
        TemperatureFeelsLikeTV = findViewById(R.id.TemperatureFeelsLikeTV);
        TemperatureTV = findViewById(R.id.TemperatureTV);
        WindSpeedTV = findViewById(R.id.WindSpeedTV);
        todayPressure = findViewById(R.id.todayPressure);
        CityCountryTV = findViewById(R.id.CityCountryTV);
        CountryTV = findViewById(R.id.CountryTV);
        MaxTempTV = findViewById(R.id.MaxTempTV);
        MinTempTV = findViewById(R.id.MinTempTV);
        jamTV = findViewById(R.id.jamTV);
        Weather24RV = findViewById(R.id.Weather24RV);
        swipeContainer = findViewById(R.id.swipeContainer);
        todayUVIndex = findViewById(R.id.todayUVIndex);
        todayHumidity = findViewById(R.id.todayHumidity);
        todaySunrise = findViewById(R.id.todaySunrise);
        todaySunset = findViewById(R.id.todaySunset);
        imageAwan = findViewById(R.id.imageAwan);
//        todayIcon = findViewById(R.id.todayIcon);



        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        Weather24RV.setLayoutManager(horizontalLayoutManagaer);

        listMainThreeDay = new ArrayList<>();
        Log.d(TAG, "temperatureArray.length : " +temperatureArray.length);

        data = new ArrayList<DataModelMainActivity>();
        for (int i = 0; i < temperatureArray.length; i++) {
            data.add(new DataModelMainActivity(imageArray[i], temperatureArray[i], statusArray[i], jamArray[i]));
        }

        mAdapter = new AdapterMainActivity(data, MainActivity.this);
        Weather24RV.setAdapter(mAdapter);

        SimpleDateFormat sdf = new SimpleDateFormat("E, dd MM yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss a");
        timeAja = sdf2.format(new Date());
        dateTime = sdf.format(new Date());

        jamTV.setText(timeAja);
        tanggalTV.setText(dateTime);

        //Instantiate Class With Your ApiKey As The Parameter
        helper = new OpenWeatherMapHelper(getString(R.string.OPEN_WEATHER_MAP_API_KEY));

        //Set Units
        helper.setUnits(Units.METRIC);

        //Set Languages
        helper.setLanguage(Languages.ENGLISH);

        buttonInfoWeather.setOnClickListener(v ->{

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_PERMISSION_REQUEST_CODE);

            } else {
                getCurrentLocation();

            }

            //if want to update manually
            helperWheaterUpdateFunciton();
        });

        buttonInfoWeatherHourly.setOnClickListener(v ->{
            helperThreeHourForecastDay();
        });

        buttonInfoWeather3.setOnClickListener(v ->{
            helper7DaysAhead();
        });

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getCurrentLocation();
        }

        //update from Medan City Only update only manually no scheduled update
        helperWheaterUpdateFunciton();

        helperThreeHourForecastDay();

        helper7DaysAhead();

        refresher();

        //periodic update every 2 minutes
        PeriodicWorkRequest dailyWeatherRequest =
                new PeriodicWorkRequest.Builder(WheaterUpdateDaily.class, 2, TimeUnit.MINUTES)
                        // Constraints
                        .build();

        WorkManager
                .getInstance(this)
                .enqueue(dailyWeatherRequest);












    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Permission is denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public static Context getAppContext() {
        return MainActivity.context;
    }

    private void helper7DaysAhead() {

    }

    private void helperThreeHourForecastDay() {
        //Weather24RV

        helper.getThreeHourForecastByGeoCoordinates(lati, longi,  new ThreeHourForecastCallback() {

            @Override
            public void onSuccess(ThreeHourForecast threeHourForecast) {

//                progressDialog.dismiss();
                Log.d(TAG,

                                "Weather Main message: " + threeHourForecast.getList().get(0).getWeather().get(0).getMain() + "\n"
                                + "Weather Description message: " + threeHourForecast.getList().get(0).getWeather().get(0).getDescription() + "\n"
                                 + "Weather Icon message: " + threeHourForecast.getList().get(0).getWeather().get(0).getIcon() + "\n"
                                 + "Weather Icon message: " + threeHourForecast.getList().get(0).getWeather().get(0).getIcon() + "\n"
                                 + "Weather Icon message: " + threeHourForecast.getList().get(0).getWeather().get(0).getIcon() + "\n"
                                 + "Weather Icon message: " + threeHourForecast.getList().get(0).getWeather().get(0).getIcon() + "\n"
                                +"Temperature: " + threeHourForecast.getList().get(0).getMain().getTemp() +"\n"
                                +"Temperature: " + threeHourForecast.getList().get(1).getMain().getTemp() +"\n"
                                +"Temperature: " + threeHourForecast.getList().get(2).getMain().getTemp() +"\n"
                                +"Temperature: " + threeHourForecast.getList().get(3).getMain().getTemp() +"\n"
                                +"Temperature Max: " + threeHourForecast.getList().get(0).getMain().getTempMax() + "\n"
                                +"feels_like: " + threeHourForecast.getList().get(0).getMain().getFeelsLike() + "\n"
                                +"feels_like: " + threeHourForecast.getList().get(1).getMain().getFeelsLike() + "\n"
                                +"feels_like: " + threeHourForecast.getList().get(2).getMain().getFeelsLike() + "\n"
                                +"feels_like: " + threeHourForecast.getList().get(3).getMain().getFeelsLike()

//                                +"temp_min: " + threeHourForecast.getName() + ", " + threeHourForecast.getSys().getCountry()+ "\n"
//                                +"temp_max: " + threeHourForecast.getWind().getDeg() + "\n"
//                                +"pressure: " + threeHourForecast + " %"+ "\n"
//                                +"sea_level: " + threeHourForecast.getSys().getSunrise()+ "\n"
//                                +"grnd_level: " + threeHourForecast.getSys().getSunset()+ "\n"
//                                +"humidity: " + threeHourForecast.getSys().getMessage()+ "\n"
//                                +"temp_kf: " + threeHourForecast.getWeather().get(0).getIcon()+ "\n"
//                                +"UV Index: " + threeHourForecast.getTimezone()
                );




            }



            @Override
            public void onFailure(Throwable throwable) {
                Log.v(TAG, throwable.getMessage());
            }
        });

    }


    public static void helperWheaterUpdateFunciton() {

        Log.d(TAG, " iam 2 minutes " ); // done

        CurrentWeather currentWeather = new CurrentWeather();

        // UV Index
        Long timezone = currentWeather.getTimezone();

        String city = currentWeather.getName();
//        String country = currentWeather.getSys().getCountry();
        Log.d(TAG, "b4 city: "  + city);
        Log.d(TAG, "b4 timezone: "  + timezone);
//        Log.d(TAG, "b4 country: "  + country);

        SimpleDateFormat sdf = new SimpleDateFormat("E, dd MM yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss a");


    }

    private static class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if (resultCode == Constants.SUCCESS_RESULT) {

                String locality = resultData.getString(Constants.LOCAITY);
//                address.setText(resultData.getString(Constants.ADDRESS));
//                locaity.setText(resultData.getString(Constants.LOCAITY));
                state = resultData.getString(Constants.STATE);
                district = resultData.getString(Constants.DISTRICT);
                LocationTV.setText(district);
                CountryTV.setText(state);
                Log.d(TAG, "district : " + district );
                Log.d(TAG, "state : " + state );

                helper.getCurrentWeatherByGeoCoordinates(lati, longi,  new CurrentWeatherCallback() {
                    @Override
                    public void onSuccess(CurrentWeather currentWeather) {

//                progressDialog.dismiss();
                        Log.d(TAG,
                                "Coordinates: " + currentWeather.getCoord().getLat() + ", "+currentWeather.getCoord().getLon() +"\n"
                                        +"Weather Description: " + currentWeather.getWeather().get(0).getDescription() + "\n"
                                        +"Temperature: " + currentWeather.getMain().getTempMax()+"\n"
                                        +"Wind Speed: " + currentWeather.getWind().getSpeed() + "\n"
                                        +"City, Country: " + currentWeather.getName() + ", " + currentWeather.getSys().getCountry()+ "\n"


                                        +"Pressure: " + currentWeather.getName() + ", " + currentWeather.getSys().getCountry()+ "\n"
                                        +"Humidity: " + currentWeather.getWind().getDeg() + "\n"
                                        +"todayWeather Humidity: " + currentWeather + " %"+ "\n"
                                        +"Sunrise: " + currentWeather.getSys().getSunrise()+ "\n"
                                        +"Sunset: " + currentWeather.getSys().getSunset()+ "\n"
                                        +"Today Icon: " + currentWeather.getSys().getMessage()+ "\n"
                                        +"Today Icon: " + currentWeather.getWeather().get(0).getIcon()+ "\n"
                                        +"UV Index: " + currentWeather.getTimezone()
                        );



//                LocationTV.setText(currentWeather.getName());
                        CityCountryTV.setText(currentWeather.getName() + ", " + currentWeather.getSys().getCountry() );
//                CountryTV.setText(locale);

//                CoordinateTV.setText(currentWeather.getName() + ", " + currentWeather.getSys().getCountry());
//                CoordinateTV.setText(currentWeather.getCoord().getLat() + ", "+currentWeather.getCoord().getLon());


                        WeatherDescriptionTV.setText(currentWeather.getWeather().get(0).getDescription());

                        Picasso.get()
                                .load(IMG_URL + currentWeather.getWeather().get(0).getIcon() +".png")
                                .resize(100, 100)
                                .into(imageAwan);



                        TemperatureTV.setText(String.valueOf(currentWeather.getMain().getTemp()) + " °C");
                        TemperatureFeelsLikeTV.setText(String.valueOf(currentWeather.getMain().getFeelsLike()) + " °C" + " (Feels Like)");
                        MaxTempTV.setText(String.valueOf(currentWeather.getMain().getTempMax()));
                        MinTempTV.setText(String.valueOf(currentWeather.getMain().getTempMin()));
                        WindSpeedTV.setText(String.valueOf(currentWeather.getWind().getSpeed()) +  " km/h");
                        todayPressure.setText(String.valueOf(currentWeather.getMain().getPressure()) +  " bar");
                        jamTV.setText(timeAja);
                        tanggalTV.setText(dateTime);

                        todayUVIndex.setText(String.valueOf(currentWeather.getTimezone()));
                        todayHumidity.setText(String.valueOf(currentWeather.getWind().getDeg()));

                        Calendar calendar = Calendar.getInstance();
                        TimeZone tz = TimeZone.getDefault();
                        calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));

//                SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
//                timeAja = sdf2.format(new Date());

                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss a", Locale.getDefault());
                        long sunrise = currentWeather.getSys().getSunrise();
                        String sunrisetimeAja = sdf.format(new Date((long)sunrise*1000));


                        long sunset = currentWeather.getSys().getSunset();
                        String sunsettimeAja = sdf.format(new Date((long)sunset*1000));

                        todaySunrise.setText(sunrisetimeAja.toString());
                        todaySunset.setText(sunsettimeAja.toString());

                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.v(TAG, throwable.getMessage());
                    }
                });
//                country.setText(resultData.getString(Constants.COUNTRY));
//                postcode.setText(resultData.getString(Constants.POST_CODE));
            } else {
                Toast.makeText(getAppContext(), resultData.getString(Constants.RESULT_DATA_KEY), Toast.LENGTH_SHORT).show();
            }
//            progressBar.setVisibility(View.GONE);
        }


    }

    public static void getCurrentLocation() {

        LocationRequest locationRequest = LocationRequest.create()
                .setInterval(100)
                .setFastestInterval(3000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(100);

        if (ActivityCompat.checkSelfPermission(getAppContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getAppContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.getFusedLocationProviderClient(getAppContext())
                .requestLocationUpdates(locationRequest, new LocationCallback() {

                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(getAppContext())
                                .removeLocationUpdates(this);
                        if (locationResult != null && locationResult.getLocations().size() > 0) {
                            int latestlocIndex = locationResult.getLocations().size() - 1;
                            lati = locationResult.getLocations().get(latestlocIndex).getLatitude();
                            longi = locationResult.getLocations().get(latestlocIndex).getLongitude();
                            CoordinateTV.setText(String.format(" %s\n  %s", lati, longi));

                            LocationTV.setText(district);
                            CountryTV.setText(state);

                            Geocoder geocoder = new Geocoder(getAppContext(), Locale.getDefault());
                            List<Address> addresses = null;
                            try {
                                addresses = geocoder.getFromLocation(lati, longi, 1);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            geocoder = new Geocoder(getAppContext(), Locale.getDefault());

                            Log.d(TAG, "lati : " + lati );
                            Log.d(TAG, "longi : " + longi );

                            try {
                                addresses = geocoder.getFromLocation(lati, longi, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                            city2 = addresses.get(0).getLocality();
                            state2 = addresses.get(0).getAdminArea();
                            String locality = addresses.get(0).getLocality();
                            String country = addresses.get(0).getCountryName();
                            String postalCode = addresses.get(0).getPostalCode();
                            String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

                            Log.d(TAG, "address 1: " + address );
                            Log.d(TAG, "city2 1: " + city2 );
                            Log.d(TAG, "state 1: " + state2 );
                            Log.d(TAG, "locality 1: " + locality );



                            Log.d(TAG, "state 1: " + state );


                            Location location = new Location("providerNA");
                            location.setLongitude(longi);
                            location.setLatitude(lati);

                            fetchaddressfromlocation(location);

                        } else {
//                            progressBar.setVisibility(View.GONE);

                        }
                    }
                }, Looper.getMainLooper());

    }

    private static void fetchaddressfromlocation(Location location) {
        Intent intent = new Intent(getAppContext(), FetchAddressIntentServices.class);
        intent.putExtra(Constants.RECEVIER, resultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, location);
        getAppContext().startService(intent);

    }


    private void refresher() {

    }





}