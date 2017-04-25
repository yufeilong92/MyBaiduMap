package com.lawyee.mybaidumap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class MainActivity extends Activity {
    private TextView info;
    double latitude;
    double longitude;
    String locationAddress;
    private ImageView mIvShowMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.btn);
        mIvShowMap = (ImageView)findViewById(R.id.iv_showMap);
        info = (TextView) findViewById(R.id.text);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BaiduMapActivity.class);
                startActivityForResult(intent, 10001);
            }
        });
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BaiduMapActivity.class);
                intent.putExtra(BaiduMapActivity.LATITUDE, latitude);
                intent.putExtra(BaiduMapActivity.LONGITUDE, longitude);
                intent.putExtra(BaiduMapActivity.ADDRESS, locationAddress);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 10001:
                    latitude = data.getDoubleExtra("latitude", 0);
                    longitude = data.getDoubleExtra("longitude", 0);
                    locationAddress = data.getStringExtra("address");
                    String locationName = data.getStringExtra("name");
                    String str = "latitude: " + latitude + "\n" + "longitude: " + longitude + "\n" +
                            "locationAddress: " + locationAddress + "\n" +
                            "locationName: " + locationName;
                    info.setText(str);
               //     http://api.map.baidu.com/staticimage?center=118.16802%2C24.53552&zoom=19&width=240&height=160&markers=118.16802%2C24.53552&markerStyles=l%2CA
                    Picasso.with(this).load("http://api.map.baidu.com/staticimage?center=" +longitude + "%2C" + latitude
                            + "&zoom=19&width=240&height=160&markers=" + longitude + "%2C" + latitude + "&markerStyles=l%2CA&qq-pf-to=pcqq.c2c").
                            into(mIvShowMap);
                    break;
            }
        }
    }
}
