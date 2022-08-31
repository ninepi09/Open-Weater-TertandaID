package com.tertandaid.openweather;

import static com.tertandaid.openweather.MainActivity.IMG_URL;
import static com.tertandaid.openweather.MainActivity.helper;
import static com.tertandaid.openweather.MainActivity.lati;
import static com.tertandaid.openweather.MainActivity.longi;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.recyclerview.widget.RecyclerView;

import com.kwabenaberko.openweathermaplib.implementation.callback.ThreeHourForecastCallback;
import com.kwabenaberko.openweathermaplib.model.threehourforecast.ThreeHourForecast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class AdapterMainActivity extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mInflater;
    private ArrayList<DataModelMainActivity> items;
    private MainActivity mActivity;
    private static final String TAG = "adapter-activity" ;

    public AdapterMainActivity(ArrayList<DataModelMainActivity> data, MainActivity activity) {
        this.items = data;
        this.mActivity = activity;
        this.mInflater = LayoutInflater.from(mActivity);
    }

    public void addItem(DataModelMainActivity result) {
        items.add(result);
    }

    public void setInflater(LayoutInflater layoutInflater){
        this.mInflater =layoutInflater;
    }



    public void insertItem(DataModelMainActivity item) {
        items.add(0, item);
    }


    public void AddResults(ArrayList<DataModelMainActivity> result) {
        items.addAll(result);
    }

    public DataModelMainActivity getItemsAt(int position){
        return  items.get(position);
    }

    @Override
    public int getItemCount() {
        return
                items.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        DataModelMainActivity model = items.get(position);

        Log.d(TAG, "size : " + position);

        helper.getThreeHourForecastByGeoCoordinates(lati, longi,  new ThreeHourForecastCallback() {

            @Override
            public void onSuccess(ThreeHourForecast threeHourForecast) {

                Log.d(TAG,

                        "Weather Main message: " + threeHourForecast.getList().get(0).getWeather().get(0).getMain() + "\n"
                                + "Weather Description message: " + threeHourForecast.getList().get(0).getWeather().get(0).getDescription() + "\n"
                                + "Weather Icon message: " + threeHourForecast.getList().get(0).getWeather().get(0).getIcon() + "\n"
                                + "Weather Icon message: " + threeHourForecast.getList().get(1).getWeather().get(0).getIcon() + "\n"
                                + "Weather Icon message: " + threeHourForecast.getList().get(2).getWeather().get(0).getIcon() + "\n"
                                + "Weather Icon message: " + threeHourForecast.getList().get(3).getWeather().get(0).getIcon() + "\n"
                                +"Temperature: " + threeHourForecast.getList().get(position).getMain().getTemp() +"\n"
                                +"Temperature Max: " + threeHourForecast.getList().get(position).getMain().getTempMax() + "\n"
                                +"feels_like: " + threeHourForecast.getList().get(position).getMain().getFeelsLike() + "\n"
                                +"dt_txt: " + threeHourForecast.getList().get(position).getDtTxt()

//                                +"temp_min: " + threeHourForecast.getName() + ", " + threeHourForecast.getSys().getCountry()+ "\n"
//                                +"temp_max: " + threeHourForecast.getWind().getDeg() + "\n"
//                                +"pressure: " + threeHourForecast + " %"+ "\n"
//                                +"sea_level: " + threeHourForecast.getSys().getSunrise()+ "\n"
//                                +"grnd_level: " + threeHourForecast.getSys().getSunset()+ "\n"
//                                +"humidity: " + threeHourForecast.getSys().getMessage()+ "\n"
//                                +"temp_kf: " + threeHourForecast.getWeather().get(0).getIcon()+ "\n"
//                                +"temp_kf: " + listThreedayModel.getMain().getTemp() + "\n"
//                                +"feels_like: " + listThreedayModel.getMain().getFeelsLike() + "\n"
//                                +"temp_min: " + listThreedayModel.getMain().getTempMin() + "\n"
//                                +"temp_max: " + listThreedayModel.getMain().getTempMax() + "\n"
//                                +"icon: " + listThreedayModel.getWeather().get(position).getIcon() + "\n"
//                                +"Desc: " + listThreedayModel.getWeather().get(position).getDescription() + "\n"
//                                +"Desc: " + listThreedayModel.getWeather().get(position).getMain() + "\n"
//                                +"UV Index: " + threeHourForecast.getTimezone()
                );

                WheatherHourViewHolder messageViewHolder = (WheatherHourViewHolder) holder;

                Picasso.get()
                        .load(IMG_URL + threeHourForecast.getList().get(position).getWeather().get(0).getIcon() +".png")
                        .resize(200, 200)
                        .into(messageViewHolder.AwanIV);

                messageViewHolder.suhuItemTV.setText(String.valueOf(threeHourForecast.getList().get(position).getMain().getTemp()) + " °C");
                messageViewHolder.jamItemTV.setText(threeHourForecast.getList().get(position).getWeather().get(0).getDescription());
                messageViewHolder.descItemTV.setText(String.valueOf(threeHourForecast.getList().get(position).getDtTxt()));

//                messageViewHolder.AwanIV.setBackgroundResource(model.getImageAwan());
//                messageViewHolder.suhuItemTV.setText(model.getTemperature());
//                messageViewHolder.jamItemTV.setText(model.getJam());


            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.v(TAG, throwable.getMessage());
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return  super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootCategoryView = mInflater.inflate(R.layout.item_wheater, parent, false);
        return new WheatherHourViewHolder(rootCategoryView, this);
    }

    private class WheatherHourViewHolder extends RecyclerView.ViewHolder  {

        private ImageView AwanIV;
        private TextView suhuItemTV;
        private TextView jamItemTV;
        private TextView descItemTV;


        private WheatherHourViewHolder(View itemView, AdapterMainActivity adapter) {
            super(itemView);
            AwanIV = (ImageView) itemView.findViewById(R.id.iconHourlyIM);
            suhuItemTV = (TextView) itemView.findViewById(R.id.suhuItemTV);
            jamItemTV = (TextView) itemView.findViewById(R.id.jamItemTV);
            descItemTV = (TextView) itemView.findViewById(R.id.descItemTV);

        }

    }
}
