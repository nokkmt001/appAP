package com.tiha.anphatsu.utils.adapterimage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import com.tiha.anphatsu.R;
import com.tiha.anphatsu.utils.AppUtils;

public class ViewImageActivity extends AppCompatActivity {
    TouchImageView imageView;
    String url = "";
    ImageView rlBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        imageView = findViewById(R.id.imageView);
        rlBack = findViewById(R.id.rlBack);
        rlBack.setOnClickListener(v -> finish());

        url = getIntent().getExtras().getString("PATHIMAGE");

        String url_image = "https://gasanphat.com/" + url;

        Glide.with(this)
                .load(url_image)
                .signature(new ObjectKey(String.valueOf(System.currentTimeMillis())))
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        imageView.setImageBitmap(AppUtils.drawableToBitmap(resource));
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

}
