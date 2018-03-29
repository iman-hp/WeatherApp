package com.example.iman.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.iman.weatherapp.models.Forecast;

import java.util.List;

/**
 * Created by Iman on 3/29/2018.
 */

public class ForcastAdapter extends BaseAdapter{
    Context mcontext;
    List<Forecast> forecast;

    public ForcastAdapter(Context mcontext, List<Forecast> forecast) {
        this.mcontext = mcontext;
        this.forecast = forecast;
    }

    @Override
    public int getCount() {
        return forecast.size();
    }

    @Override
    public Object getItem(int position) {
        return forecast.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row= LayoutInflater.from(mcontext).inflate(R.layout.listview_week_item,parent,false);
        TextView txt_nameweek=row.findViewById(R.id.txt_nameweek);
        ImageView img_weekday=row.findViewById(R.id.img_weekday);
        TextView txt_lowday=row.findViewById(R.id.txt_lowweek);
        TextView txt_highday=row.findViewById(R.id.txt_highweek);


        String low=forecast.get(position).getLow();
        low=PublicMethods.getC(low);

        String high=forecast.get(position).getHigh();
        high=PublicMethods.getC(high);


        txt_nameweek.setText(forecast.get(position).getDay());
        txt_lowday.setText(low+(char) 0x00B0);
        txt_highday.setText(high+(char) 0x00B0);

      if(forecast.get(position).getText().contains("Sunny"))
          img_weekday.setImageResource(R.drawable.sunny);
      else if(forecast.get(position).getText().contains("Partly Cloudy"))
      img_weekday.setImageResource(R.drawable.cloudy);
      else
          img_weekday.setImageResource(R.drawable.rainy);
        return row;
    }
}
