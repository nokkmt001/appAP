package com.tiha.anphat.ui.pay.history.vote;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tiha.anphat.R;
import com.tiha.anphat.data.entities.ReasonEvaluate;
import com.tiha.anphat.data.entities.condition.EvaluateCondition;
import com.tiha.anphat.databinding.ActivityVoteBinding;
import com.tiha.anphat.ui.base.BaseActivity;
import com.tiha.anphat.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class VoteEmployeeActivity extends BaseActivity implements VoteContract.View {
    ActivityVoteBinding binding;
    VoteAdapter adapter;
    VotePresenter presenter;
    Boolean isCheck = true;
    AddImageAdapter addImageAdapter;
    List<ReasonEvaluate> listChoose = new ArrayList<>();
    String[] permissionsRequired = {Manifest.permission.CAMERA};
    String bitmapImage = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vote;
    }

    @Override
    protected void initView() {
        CheckCamera();
        checkSelfPermission(permissionsRequired);
        binding = ActivityVoteBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setHeader();

        adapter = new VoteAdapter();
        binding.rcl.setAdapter(adapter);
        addImageAdapter = new AddImageAdapter(this,new ArrayList<String>());
        binding.rclImage.setLayoutManager( new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        binding.rclImage.setAdapter(addImageAdapter);

        checkValidate();

        binding.buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.getListChoose().size() == 0) {
                    showMessage("Bạn chưa chọn đề xuất đánh giá");
                } else {
                    EvaluateCondition condition = new EvaluateCondition();
                    condition.setBinhLuan(binding.inputComment.getText().toString());
                    condition.setListLyDoDanhGiaSaoo(adapter.getListChoose());
//                    condition.setHinhAnh();
//                    presenter.InsertVote();
                }
            }
        });
        setRating();

        binding.textChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChooseFile();
            }
        });
    }

    public void ResultImageBitMap(Bitmap bitmap) {
        if (bitmap==null) return;
        addImageAdapter.add(AppUtils.formatBitMapToString(bitmap));
        Toast.makeText(this,"GG",Toast.LENGTH_SHORT).show();
    }



    private void CheckCamera() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            checkSelfPermission(permissionsRequired);
        } else {
            showChooseFile();
        }
    }

    private void setRating() {
        binding.rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if (v == 0) {
                    showMessage("Bạn phải chọn số sao");
                } else {
                    presenter.GetListVote(String.valueOf(v));
                }
            }
        });
    }

    public void checkValidate() {
        if (isCheck) {
            binding.buttonOK.setEnabled(true);
            binding.buttonOK.setTextColor(getResources().getColor(R.color.White));
            binding.buttonOK.setBackgroundResource(R.drawable.bg_button_dark_no_radius);
//        } else {
//            binding.buttonOK.setEnabled(false);
//            binding.buttonOK.setTextColor(getResources().getColor(R.color.text_disable));
//            binding.buttonOK.setBackgroundResource(R.drawable.bg_button_light);
        }
    }

    private void setHeader() {
        binding.layoutHeader.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.layoutHeader.textTitle.setText(getText(R.string.title_vote_employee));
    }

    @Override
    protected void initData() {
        presenter = new VotePresenter(this);
        presenter.GetListVote("1");
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onGetListVoteSuccess(List<ReasonEvaluate> list) {
        if (list.size() == 0) {
            showNoResult();
        } else {
            adapter.clear();
            adapter.addAll(list);
            adapter.addAll(list);
        }

    }

    @Override
    public void onGetListVoteError(String error) {
        showMessage(error);
    }

    @Override
    public void onInsertVoteSuccess() {

    }

    @Override
    public void onInsertVoteError(String error) {
        showMessage(error);
    }
}
