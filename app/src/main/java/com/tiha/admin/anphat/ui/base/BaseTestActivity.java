package com.tiha.admin.anphat.ui.base;

import android.Manifest;
import android.app.Dialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.ViewDataBinding;
import androidx.viewbinding.ViewBinding;

import com.tiha.admin.anphat.R;
import com.tiha.admin.anphat.utils.ImageFilePath;
import com.tiha.admin.anphat.utils.ImageUtils;
import com.tiha.admin.anphat.utils.NetworkUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static com.tiha.admin.anphat.utils.ImageUtils.getOutputMediaFileUri;

public abstract class BaseTestActivity<T extends ViewBinding> extends AppCompatActivity implements View.OnClickListener {
    Dialog progressDialog;
    String error = "";
    private final int REQUEST_MULTIPLE_PERMISSIONS = 100;
    String[] permissionsMain = {};
    private SpeechRecognizer speechRecognizer;
    int count = 0;
    String imageStoragePath = "";
    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_GALLERY = 2;
    protected T binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        if (!NetworkUtils.isNetworkConnected(this)) {
            error = getResources().getString(R.string.error_msg_no_internet);
            showMessage(error);
        }
        binding = getViewBinding();
        setContentView(binding.getRoot());
        initView();
        initData();
    }

    public abstract T getViewBinding();

    protected abstract void initView();

    protected abstract void initData();

    public <N extends View> N bind(int id) {
        return findViewById(id);
    }

    protected void showToast(String mToastMsg) {
        Toast.makeText(this, mToastMsg, Toast.LENGTH_LONG).show();
    }

    protected void alertDialog(String title, String message, String btnPos, String btnNeutral, DialogInterface.OnClickListener ocListener) {
        AlertDialog.Builder db = new AlertDialog.Builder(this);
        db.setTitle(title);
        db.setMessage(message);
        db.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        if (btnPos != null) db.setPositiveButton(btnPos, ocListener);
        if (btnNeutral != null) db.setNeutralButton(btnNeutral, ocListener);
//        db.setIcon(android.R.drawable.ic_dialog_alert);
        db.show();
    }

    protected void hideKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (getCurrentFocus() != null)
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            Log.e("MultiBackStack", "Failed to add fragment to back stack", e);
        }
    }

    public void checkSelfPermission(String[] permissionsRequired) {
        permissionsMain = permissionsRequired;
        for (String tt : permissionsRequired) {
            if (ActivityCompat.checkSelfPermission(this, tt) != PackageManager.PERMISSION_GRANTED) {
                {
                    ActivityCompat.requestPermissions(this, permissionsRequired, REQUEST_MULTIPLE_PERMISSIONS);
                    //return;
                }
            }
        }
    }

    public void showProgressDialog(boolean isShow) {
        if (isShow) {
            progressDialog = new Dialog(this);
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(R.color.colorTransparent);
            progressDialog.setCancelable(false);
            progressDialog.setContentView(R.layout.dialog_progressbar_waiting);
            progressDialog.show();
        } else {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }
    }

    protected void showMessage(String error) {
        error = error.isEmpty() ? getString(R.string.error_msg_unknown) : error;
        if (!NetworkUtils.isNetworkConnected(this)) {
            error = getString(R.string.error_msg_no_internet);
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.title_error_msg))
                .setMessage(error)
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.dialog_btn_ok), (dialog, id) -> dialog.cancel());
        final AlertDialog alert = builder.create();
        alert.show();
    }

    protected void showInfo(String error) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.title_info_msg))
                .setMessage(error)
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.dialog_btn_ok), (dialog, id) -> dialog.cancel());
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void showNoResult() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông tin")
                .setMessage(getResources().getString(R.string.noresult_msg))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.dialog_btn_ok), (dialog, id) -> dialog.cancel());
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        onResult(requestCode, resultCode, data);
        onResultFile(requestCode, resultCode, data);
    }

    protected void onResult(int requestCode, int resultCode, Intent data) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_MULTIPLE_PERMISSIONS) {//Kiem tra tat ca quyen can cap
            boolean allgranted = false;
            for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }

            for (String ii : permissionsMain) {
                if (!allgranted && ActivityCompat.shouldShowRequestPermissionRationale(this, ii)) {
                    showMessagePermissions();
                }
            }
        }
    }

    private void showMessagePermissions() {
        new AlertDialog.Builder(this).setTitle("QUYỀN ỨNG DỤNG").setMessage("Ứng dụng cần được cấp nhiều quyền hơn.")
                .setPositiveButton("CẤP QUYỀN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(BaseTestActivity.this, permissionsMain, REQUEST_MULTIPLE_PERMISSIONS);
                    }
                })
                .setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        finish();
                    }
                }).show();
    }

    protected void speedText(final EditText text, final ImageView imageView) {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        checkSelfPermission(new String[]{Manifest.permission.RECORD_AUDIO});
        final Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        imageView.setOnClickListener(view -> {
            if (count == 0) {
                imageView.setImageResource(R.drawable.ic_mic_black_24dp);
                speechRecognizer.startListening(speechRecognizerIntent);
                count = 1;
            } else {
                imageView.setImageResource(R.drawable.ic_baseline_off_mic);
                speechRecognizer.stopListening();
                count = 0;
            }
        });
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {
            }

            @Override
            public void onBeginningOfSpeech() {
                text.setText("");
                text.setHint("Listening...");
            }

            @Override
            public void onRmsChanged(float v) {
            }

            @Override
            public void onBufferReceived(byte[] bytes) {
            }

            @Override
            public void onEndOfSpeech() {
            }

            @Override
            public void onError(int i) {
            }

            @Override
            public void onResults(Bundle bundle) {
                imageView.setImageResource(R.drawable.ic_baseline_off_mic);
                ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                assert data != null;
                text.setText(data.get(0));
            }

            @Override
            public void onPartialResults(Bundle bundle) {
            }

            @Override
            public void onEvent(int i, Bundle bundle) {
            }
        });
    }

    public void showChooseFile() {
        final List<String> listChoose = new ArrayList<>();
        listChoose.add("Chụp ảnh");
        listChoose.add("Chọn ảnh");

        final CharSequence[] items = listChoose.toArray(new CharSequence[0]);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thêm hình ảnh");
        builder.setPositiveButton("Hủy bỏ", (dialogInterface, i) -> dialogInterface.dismiss());
        builder.setItems(items, (dialogInterface, position) -> {
            switch (listChoose.get(position)) {
                case "Chụp ảnh":
                    cameraIntent();
                    break;
                case "Chọn ảnh":
                    galleryIntent();
                    break;
                default:
                    break;
            }
        });
        builder.show();
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = ImageUtils.getOutputMediaFile(MEDIA_TYPE_IMAGE);
        if (file != null) {
            imageStoragePath = file.getAbsolutePath();
        }

        Uri fileUri = getOutputMediaFileUri(getApplicationContext(), file);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), REQUEST_GALLERY);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected void onResultFile(int requestCode, int resultCode, @Nullable Intent intent) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CAMERA:
                    File file = new File(imageStoragePath);
                    ImageUtils.refreshGallery(getApplicationContext(), imageStoragePath);
                    Bitmap bitmap = ImageUtils.optimizeBitmap(8, imageStoragePath, this);
                    ResultImageBitMap(bitmap);
                    break;
                case REQUEST_GALLERY:
                    onSelectFromGalleryResult(intent);
                    break;
                default:
                    break;
            }
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        if (data == null) return;
        if (data.getClipData() != null) {
            ClipData mClipData = data.getClipData();
//            InsertMultiPicture(mClipData.)
            for (int i = 0; i < mClipData.getItemCount(); i++) {
                ClipData.Item item = mClipData.getItemAt(i);
                InsertPictureFromGallery(item.getUri());
            }
        } else if (data.getData() != null) {
            InsertPictureFromGallery(data.getData());
        }
    }

    public void ResultImageBitMap(Bitmap bitmap) {
    }

    public void ResultGallery(List<Bitmap> list) {
    }

    public void InsertMultiPicture(List<Uri> list) {

    }

    public void InsertPictureFromGallery(Uri uriPicture) {
        String filePath = ImageFilePath.getPath(this, uriPicture);
        File file = new File(filePath);
        Bitmap bitmap = ImageUtils.optimizeBitmap(8, filePath, this);
        ResultImageBitMap(bitmap);
    }
}

