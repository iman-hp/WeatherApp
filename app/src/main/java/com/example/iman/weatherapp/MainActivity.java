package com.example.iman.weatherapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iman.weatherapp.models.Forecast;
import com.example.iman.weatherapp.models.YahooWeather;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity  {

    TextView txt_city;
    TextView txt_country;
    TextView txt_bignum;
    ImageView img_basecity;
    TextView txt_wind;
    TextView txt_humidity;
    TextView txt_direction;
    ListView list_week;
    String recived_city;
    TextView txt_date;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindviews();
        progressDialog=new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please Wait");



        Intent recivedIntent=getIntent();
        recived_city=recivedIntent.getStringExtra("name");
        String url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22" + recived_city + "%2C%20ak%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new TextHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                progressDialog.show();
            }

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Toast.makeText(MainActivity.this, throwable.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                ParseServerByGSON(s);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                progressDialog.dismiss();
            }
        });

    }






    void bindviews() {
        txt_city = (TextView) findViewById(R.id.txt_city);
        txt_country = (TextView) findViewById(R.id.txt_country);
        txt_bignum = (TextView) findViewById(R.id.txt_bignum);
        img_basecity = (ImageView) findViewById(R.id.img_basecity);
        txt_humidity = (TextView) findViewById(R.id.txt_humidity);
        txt_direction = (TextView) findViewById(R.id.txt_direction);
        list_week = (ListView) findViewById(R.id.listview_week);
        txt_wind = (TextView) findViewById(R.id.txt_wind);
        txt_date=findViewById(R.id.txt_date);




    }



    void ParseServerByGSON(String serverrespance) {
        Gson gson = new Gson();
        YahooWeather yahoo = gson.fromJson(serverrespance, YahooWeather.class);
        String city = yahoo.getQuery().getResults().getChannel().getLocation().getCity();
        String country = yahoo.getQuery().getResults().getChannel().getLocation().getCountry();
        String temp = yahoo.getQuery().getResults().getChannel().getItem().getCondition().getTemp();
        String wind = yahoo.getQuery().getResults().getChannel().getWind().getSpeed();
        String humidity = yahoo.getQuery().getResults().getChannel().getAtmosphere().getHumidity();
        String direction = yahoo.getQuery().getResults().getChannel().getWind().getDirection();
        List<Forecast> forcastsmodels = yahoo.getQuery().getResults().getChannel().getItem().getForecast();
        String text = yahoo.getQuery().getResults().getChannel().getItem().getCondition().getText();
        String date=yahoo.getQuery().getResults().getChannel().getItem().getCondition().getDate();

        ForcastAdapter adapter = new ForcastAdapter(MainActivity.this, forcastsmodels);
        list_week.setAdapter(adapter);


        temp=PublicMethods.getC(temp);

        txt_city.setText(city);
        txt_country.setText(country);
        txt_bignum.setText(temp+(char) 0x00B0+"C");
        txt_wind.setText(wind);
        txt_date.setText(date);
        txt_direction.setText(direction);
        txt_humidity.setText(humidity);
        if (text.contains("Sunny"))
            img_basecity.setImageResource(R.drawable.sunny);
        else if (text.contains("rain"))
            img_basecity.setImageResource(R.drawable.rainy);
        else
            img_basecity.setImageResource(R.drawable.cloudy);


    }

}
