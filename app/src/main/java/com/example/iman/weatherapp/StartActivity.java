package com.example.iman.weatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class StartActivity extends AppCompatActivity {
 EditText edt_namecity;
 ImageButton img_go;
 String city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        bind();



    }
    void bind(){
        edt_namecity=findViewById(R.id.edt_namecity);
        img_go=findViewById(R.id.img_go);
        img_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                city=edt_namecity.getText().toString();
                Intent destinationIntent =new Intent(StartActivity.this,MainActivity.class);
                destinationIntent.putExtra("name",city);
                startActivity(destinationIntent);
            }
        });



    }
}
