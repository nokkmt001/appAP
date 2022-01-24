package com.tiha.admin.anphat;

import android.content.Intent;
import android.view.View;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.tiha.admin.anphat.databinding.ActivityTextBinding;
import com.tiha.admin.anphat.ui.base.BaseActivity;
import com.tiha.admin.anphat.R;
import com.tiha.admin.anphat.data.AppPreference;
//import com.tiha.admin.anphat.databinding.ActivityTextBinding;

public class TextActivity extends BaseActivity {
    AppPreference appPreference;
    ActivityTextBinding binding;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_text;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
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
