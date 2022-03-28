package com.anphat.supplier.utils.adapterimage;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.matthewtamlin.sliding_intro_screen_library.indicators.DotIndicator;
import com.anphat.supplier.R;
import com.anphat.supplier.ui.base.BaseActivity;
import com.anphat.supplier.utils.PublicVariables;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class ActivityImage extends BaseActivity {
    ViewImage viewImage;
    ViewPager viewPagerImage;
    DotIndicator dotsIndicator;

    List<String> listAllData = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.view_image_fragment;
    }

    @Override
    protected void initView() {
        viewPagerImage = findViewById(R.id.viewPagerImage);
        dotsIndicator = findViewById(R.id.dotsIndicator);
    }

    @Override
    protected void initData() {
        listAllData = PublicVariables.listImageVote;
        viewImage = new ViewImage(this, listAllData);
        viewPagerImage.setAdapter(viewImage);
        if (listAllData.size() > 1) {
            dotsIndicator.setNumberOfItems(listAllData.size());
        } else dotsIndicator.setVisibility(View.GONE);
        viewPagerImage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectDot(dotsIndicator, position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {

    }


    public void selectDot(DotIndicator dotIndicator, Integer position) {
        dotIndicator.setSelectedItem(position, true);
    }

    public void DownLoadFile(String url, String nameFile) {
        try {
            File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS), "myfolder");

            if (!imageStorageDir.exists()) {
                //noinspection ResultOfMethodCallIgnored
                imageStorageDir.mkdirs();
            }

            String file = getString(R.string.app_name) + "_" + nameFile;

            DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            Uri downloadUri = Uri.parse(url);
            DownloadManager.Request request = new DownloadManager.Request(downloadUri);
            String folder = "/Download";
            try {
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setDescription("Downloading...")
                        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, file)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            } catch (Exception e) {
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setDescription("Downloading...")
                        .setDestinationInExternalPublicDir(folder, file)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); // v28<=
            }

            dm.enqueue(request);
            Toast.makeText(this, "DownLoad Success", Toast.LENGTH_LONG).show();
        } catch (IllegalStateException ex) {
            Toast.makeText(getApplicationContext(), "Storage Error", Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        } catch (Exception ex) {
            // just in case, it should never be called anyway
            Toast.makeText(getApplicationContext(), "Unable to save image", Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }
}
