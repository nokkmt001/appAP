package com.tiha.admin.anphat.ui.update;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;

import com.tiha.admin.anphat.BuildConfig;
import com.tiha.admin.anphat.R;
import com.tiha.admin.anphat.data.entities.ChangeLogInFo;
import com.tiha.admin.anphat.databinding.ActivityUpdateBinding;
import com.tiha.admin.anphat.ui.base.BaseActivity;
import com.tiha.admin.anphat.utils.AppConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Pattern;

public class UpdateActivity extends BaseActivity implements UpdateContarct.View{
    ActivityUpdateBinding binding;
    UpdatePresenter presenter;
    String[] permissionsRequired ={android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.REQUEST_INSTALL_PACKAGES};
    @Override
    protected int getLayoutId() {
        return R.layout.activity_booking;
    }

    @Override
    protected void initView() {
        binding = ActivityUpdateBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.layoutHeader.textTitle.setText(R.string.title_update);
    }

    @Override
    protected void initData() {
        presenter = new UpdatePresenter(this);
        presenter.GetChangeLog();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onGetChangeLogSuccess(ChangeLogInFo inFo) {
        AppConstants.URL_SERVER = "http://anphatapp.tiha.vn/";
        AppConstants.SERVER_NAME = "anphatapp.tiha.vn/";

        String version = "";

        try {
            PackageInfo pInfo = UpdateActivity.this.getPackageManager().getPackageInfo(UpdateActivity.this.getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (!inFo.getLatestVersion().equals(version)) {
            URL url = null;
            try {
                url = new URL(inFo.getUrl());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            checkSelfPermission(permissionsRequired);
            isShowUpdate(url);
        } else {
            showInfo( "Phần mềm đã được cập nhật.");
        }
    }

    @Override
    public void onGetChangeLogError(String error) {
        showMessage(error);
    }

    public void isShowUpdate(final URL url) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this);
        builder.setTitle("Cập nhật")
                .setMessage("Vui lòng cập nhật phần mềm để tiếp tục sử dụng")
                .setCancelable(false)
                .setPositiveButton("CÓ", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                        showProgress(true);
                        DownloadFileUpdateAplicationTask downloadFileUpdateAplicationTask = new DownloadFileUpdateAplicationTask(url);
                        downloadFileUpdateAplicationTask.execute();
                    }
                })
                .setNegativeButton("KHÔNG", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {

                        moveTaskToBack(true);
                        finishAffinity();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
    ProgressBar progressBarDownload;
    TextView tvProgress;
    TextView tvTotalProgress;
    AlertDialog alertDialog;

    private void showProgress(boolean isShow) {
        if (isShow) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.dialog_progressbar_download, null);
            progressBarDownload = view.findViewById(R.id.progressBar);
            tvProgress = view.findViewById(R.id.tvProgress);
            tvTotalProgress = view.findViewById(R.id.tvTotalProgress);
            alertDialog = new AlertDialog.Builder(this)
                    .setTitle("Please Wait...")
                    .setCancelable(false)
                    .setView(view)
                    .create();
            alertDialog.show();

        } else {
            alertDialog.dismiss();
        }
    }

    private class DownloadFileUpdateAplicationTask extends AsyncTask<String, String, String> {

        URL url;

        public DownloadFileUpdateAplicationTask(URL url) {
            this.url = url;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                String newfileName = "BMAnPhatNB.apk";
                URLConnection conn = url.openConnection();
                conn.connect();
                // getting file length
                int lenghtOfFile = conn.getContentLength();

                FileOutputStream fos;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    File newAPKFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), newfileName);
                    fos = new FileOutputStream(newAPKFile);
                } else {
                    fos = openFileOutput(newfileName, MODE_WORLD_READABLE | MODE_WORLD_WRITEABLE);
                }
                long total = 0;
                // Download the new APK file
                InputStream is = conn.getInputStream();//httpConn.getInputStream();
                byte[] buffer = new byte[1024];
                int count = 0;
                while ((count = is.read(buffer)) != -1) {
                    total += count;
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                    fos.write(buffer, 0, count);
                }
                fos.flush();
                fos.close();
                is.close();
            } catch (IOException e) {
                return e.getMessage();
            }
            return null;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            int progress = Integer.parseInt(values[0]);
            progressBarDownload.setProgress(progress);
            tvProgress.setText(String.valueOf(progress) + "%");
            tvTotalProgress.setText(String.valueOf(progress) + "/100");
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            showProgress(false);
            if (result == null || result.isEmpty())
                installApk();
        }

    }

    private void installApk() {
        try {
            String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";
            String fileName = "BMAnPhatNB.apk";
            filePath += fileName;
            File file = new File(filePath);
            if (file.exists()) {
                String[] fileNameArray = file.getName().split(Pattern.quote("."));
                if (fileNameArray[fileNameArray.length - 1].equals("apk")) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Uri downloaded_apk = getFileUri(file);
                        Intent intent = new Intent(Intent.ACTION_VIEW).setDataAndType(downloaded_apk,
                                "application/vnd.android.package-archive");
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(file),
                                "application/vnd.android.package-archive");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Uri getFileUri(File file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", file);
        } else {
            return Uri.fromFile(file);
        }
    }
}
