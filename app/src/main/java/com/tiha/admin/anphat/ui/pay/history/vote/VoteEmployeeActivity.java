package com.tiha.admin.anphat.ui.pay.history.vote;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tiha.admin.anphat.ui.base.BaseActivity;
import com.tiha.admin.anphat.ui.base.BaseEventClick;
import com.tiha.admin.anphat.R;
import com.tiha.admin.anphat.data.entities.ReasonEvaluate;
import com.tiha.admin.anphat.data.entities.condition.EvaluateCondition;
import com.tiha.admin.anphat.databinding.ActivityVoteBinding;
import com.tiha.admin.anphat.utils.AppUtils;
import com.tiha.admin.anphat.utils.PublicVariables;
import com.tiha.admin.anphat.utils.adapterimage.ActivityImage;

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
    RatingBar rating;
    RecyclerView rclImage;
    List<String> listImageString = new ArrayList<>();

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
        rating = findViewById(R.id.rating);

        setHeader();

        adapter = new VoteAdapter();
        binding.rcl.setAdapter(adapter);
        addImageAdapter = new AddImageAdapter(this, new ArrayList<String>());
        binding.rclImage.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.rclImage.setAdapter(addImageAdapter);

        checkValidate();
        binding.buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.getListChoose().size() == 0) {
                    showMessage("Bạn chưa chọn đề xuất đánh giá");
                } else {
                    String gg = "";
                    showProgressDialog(true);
                    EvaluateCondition condition = new EvaluateCondition();
                    condition.setSoSao(5);
//                    condition.setEmployeeID();
                    condition.setBinhLuan(binding.inputComment.getText().toString());
                    condition.setListLyDoDanhGiaSaoo(adapter.getListChoose());
                    for (String string : listImageString) {
                        gg += string + ",";
                    }
                    if (gg.length() > 0) {
                        condition.setHinhAnh(gg.substring(0, gg.length() - 1));

                    }
//                    presenter.InsertVote(condition);
                }
            }
        });
//        setRating();

        binding.textChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChooseFile();
            }
        });
        rating.setRating(5);

        this.rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ratingBar.setRating(v);
                Toast.makeText(VoteEmployeeActivity.this, "Gg", Toast.LENGTH_SHORT).show();
                if (v == 0) {
                    showMessage("Bạn phải chọn số sao");
                } else {
                    presenter.GetListVote(String.valueOf(v));
                }
            }
        });

        setAdapterClick();
    }

    private void setAdapterClick() {
        addImageAdapter.setOnClickListener(new BaseEventClick.OnClickListener() {
            @Override
            public void onClick(View view, final int position) {
                switch (view.getId()) {
                    case R.id.imageDelete:
                        alertDialog("Xóa hình ảnh", "Bạn chắc chắn xóa hình ảnh này?", null, "ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                listImageString.remove(addImageAdapter.getItem(position));
                                addImageAdapter.removeItem(position);

                            }
                        });
                        break;
                    case R.id.imageAdd:
                        PublicVariables.listImageVote = addImageAdapter.getListAllData();
                        Intent intent = new Intent(VoteEmployeeActivity.this, ActivityImage.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void ResultImageBitMap(Bitmap bitmap) {
        if (bitmap == null) return;
        String gg = "";
        gg = AppUtils.formatBitMapToString(bitmap);
        if (gg.equals("")) return;
        listImageString.add(gg);
        addImageAdapter.add(gg);
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
                binding.rating.setRating(v);
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
        presenter.GetListVote("5");
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
        }

    }

    @Override
    public void onGetListVoteError(String error) {
        showMessage(error);
    }

    @Override
    public void onInsertVoteSuccess() {
        finish();
        showProgressDialog(false);
    }

    @Override
    public void onInsertVoteError(String error) {
        showMessage(error);
        showProgressDialog(false);
    }
}
