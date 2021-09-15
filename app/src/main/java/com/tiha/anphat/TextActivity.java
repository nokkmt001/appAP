package com.tiha.anphat;

import android.content.Intent;
import android.view.View;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.tiha.anphat.data.AppPreference;
import com.tiha.anphat.databinding.ActivityTextBinding;
import com.tiha.anphat.ui.base.BaseActivity;

public class TextActivity extends BaseActivity {
    AppPreference appPreference;
    ActivityTextBinding binding;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_text;
    }

    @Override
    protected void onLoadData() {

    }

    @Override
    protected void onInit() {
        binding = ActivityTextBinding.inflate(getLayoutInflater());
        binding.buttonEnd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });
        binding.recyclerViewText.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
//               binding.layoutHeader.
//        textMain = findViewById(R.id.textMain);
//        buttonEnd = findViewById(R.id.buttonEnd);
//        buttonEndWhite = findViewById(R.id.buttonEndWhite);
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
