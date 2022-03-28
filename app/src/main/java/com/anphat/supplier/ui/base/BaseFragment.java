package com.anphat.supplier.ui.base;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.anphat.supplier.R;
import com.anphat.supplier.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.Locale;

public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    Dialog progressDialog;
    private final int REQUEST_MULTIPLE_PERMISSIONS = 100;
    String[] permissionsMain = {};
    private SpeechRecognizer speechRecognizer;
    int count = 0;
    String error = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        if (!NetworkUtils.isNetworkConnected(requireContext())) {
            error = getResources().getString(R.string.error_msg_no_internet);
            showMessage(error);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(getLayoutID(), null);
        initView(root);
        initData();
        return root;
    }

    @LayoutRes
    protected abstract int getLayoutID();

    protected abstract void initView(View view);

    protected abstract void initData();

    public void showProgressDialog(boolean isShow) {
        if (isShow) {
            progressDialog = new Dialog(getActivity());
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            progressDialog.getWindow().setBackgroundDrawableResource(R.color.colorTransparent);
            progressDialog.setCancelable(false);
            progressDialog.setContentView(R.layout.dialog_progressbar_waiting);
            progressDialog.show();

        } else {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }
    }

    protected void alertDialog(String title, String message, String btnPos, String btnNeutral, DialogInterface.OnClickListener ocListener) {
        AlertDialog.Builder db = new AlertDialog.Builder(getContext());
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

    public <T extends View> T bind(View view, int id) {
        return view.findViewById(id);
    }

    public void checkSelfPermission(String[] permissionsRequired) {
        permissionsMain = permissionsRequired;
        for (String tt : permissionsRequired) {
            if (ActivityCompat.checkSelfPermission(getActivity(), tt) != PackageManager.PERMISSION_GRANTED) {
                {
                    ActivityCompat.requestPermissions(getActivity(), permissionsRequired, REQUEST_MULTIPLE_PERMISSIONS);
                    //return;
                }
            }
        }
    }

    public void showNoResult(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Thông tin")
                .setMessage(getActivity().getResources().getString(R.string.noresult_msg))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.dialog_btn_ok), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void showMessage(String error) {
        error = error.isEmpty() ? getActivity().getResources().getString(R.string.error_msg_unknown) : error;
        if (!NetworkUtils.isNetworkConnected(getActivity())) {
            error = getActivity().getResources().getString(R.string.error_msg_no_internet);
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getActivity().getResources().getString(R.string.title_error_msg))
                .setMessage(error)
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.dialog_btn_ok), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void speedText(final EditText text, final ImageView imageView) {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getActivity());
        checkSelfPermission(new String[]{Manifest.permission.RECORD_AUDIO});
        final Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count == 0) {
                    imageView.setImageResource(R.drawable.ic_mic_black_24dp);
                    speechRecognizer.startListening(speechRecognizerIntent);
                    count = 1;
                } else {
                    imageView.setImageResource(R.drawable.ic_baseline_off_mic);
                    speechRecognizer.stopListening();
                    count = 0;
                }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_MULTIPLE_PERMISSIONS:
                //Kiem tra tat ca quyen can cap
                boolean allgranted = false;
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        allgranted = true;
                    } else {
                        allgranted = false;
                        break;
                    }
                }

                for (String ii : permissionsMain) {
                    if (!allgranted && ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), ii)) {
                        showMessagePermissions();
                    } else {

                    }
                }
                break;
            default:
                break;
        }
    }

    private void showMessagePermissions() {
        new AlertDialog.Builder(getActivity())
                .setTitle("QUYỀN ỨNG DỤNG")
                .setMessage("Ứng dụng cần được cấp nhiều quyền hơn.")
                .setPositiveButton("CẤP QUYỀN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(getActivity(), permissionsMain, REQUEST_MULTIPLE_PERMISSIONS);
                    }
                })
                .setNegativeButton("HỦY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }
}