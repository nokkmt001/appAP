package com.tiha.anphat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;

import com.tiha.anphat.data.AppPreference;
import com.tiha.anphat.ui.base.BaseActivity;

public class TextActivity extends BaseActivity {
    private Switch buttonEnd, buttonEndWhite;
    private TextView textMain;
    AppPreference appPreference;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_text;
    }

    @Override
    protected void onLoadData() {

    }

    @Override
    protected void onInit() {

        textMain = findViewById(R.id.textMain);
        buttonEnd = findViewById(R.id.buttonEnd);
        buttonEndWhite = findViewById(R.id.buttonEndWhite);
//        appPreference = new AppPreference(TextActivity.this);
//        if (appPreference.isLogin()){
//            buttonEnd.setChecked(true);
//        } else {
//            buttonEnd.setChecked(false);
//        }
//        buttonEnd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                    setTheme(R.style.AppTheme);
//                    textMain.setText("Dark");
//                    appPreference.setLogin(true);
//                } else {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                    setTheme(R.style.DarTheme);
//                    textMain.setText("Light");
//                    appPreference.setLogin(false);
//                }
//                resset();
//            }
//        });
    }

    private void resset() {
        Intent intent = new Intent(getApplicationContext(), TextActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {

    }
}
