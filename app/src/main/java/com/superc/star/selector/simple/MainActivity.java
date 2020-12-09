package com.superc.star.selector.simple;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.superc.star.selector.SuperRationBar;

public class MainActivity extends AppCompatActivity {

    private SuperRationBar rationBar;
    private SuperRationBar rationBar1;
    private SuperRationBar rationBar2;
    private SuperRationBar rationBar3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rationBar = findViewById(R.id.rationBar);
        rationBar.setImageResIds(R.mipmap.star_full_1, R.mipmap.star_half_1, R.mipmap.star_empty_1)
                .setSelectNumber(2.4f)
                .launcher();
        rationBar1 = findViewById(R.id.rationBar1);
        rationBar1.setImageResIds(R.mipmap.star_full_1, 0, R.mipmap.star_empty_1)
                .setSelectNumber(2.4f)
                .launcher();
        rationBar2 = findViewById(R.id.rationBar2);
        rationBar2.setImageResIds(R.mipmap.star_full_1, R.mipmap.star_half_1, R.mipmap.star_empty_1)
                .setSelectNumber(2.4f)
                .launcher();
        rationBar3 = findViewById(R.id.rationBar3);
        rationBar3.setImageResIds(R.mipmap.star_full_1, 0, R.mipmap.star_empty_1)
                .setSelectNumber(2.4f)
                .launcher();


    }
}