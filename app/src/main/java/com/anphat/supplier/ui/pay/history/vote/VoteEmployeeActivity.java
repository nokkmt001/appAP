package com.anphat.supplier.ui.pay.history.vote;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.anphat.supplier.R;
import com.anphat.supplier.data.entities.condition.EvaluateCondition;
import com.anphat.supplier.data.entities.order.BookingInfo;
import com.anphat.supplier.databinding.ActivityVoteBinding;
import com.anphat.supplier.ui.base.BaseMVVMActivity;
import com.anphat.supplier.viewmodel.VoteViewModel;
import com.anphat.supplier.utils.AppUtils;
import com.anphat.supplier.utils.PublicVariables;
import com.anphat.supplier.utils.adapterimage.ActivityImage;

import java.util.ArrayList;
import java.util.List;

public class VoteEmployeeActivity extends BaseMVVMActivity<ActivityVoteBinding, VoteViewModel> {
    VoteAdapter adapter;
    Boolean isCheck = true;
    AddImageAdapter addImageAdapter;
    String[] permissionsRequired = {Manifest.permission.CAMERA};
    List<String> listImageString = new ArrayList<>();
    BookingInfo infoItem = null;
    String sao = "5";
    @Override
    protected Class<VoteViewModel> getClassVM() {
        return VoteViewModel.class;
    }

    @Override
    public ActivityVoteBinding getViewBinding() {
        return ActivityVoteBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        CheckCamera();
        checkSelfPermission(permissionsRequired);
        setHeader();
        adapter = new VoteAdapter();
        binding.rcl.setAdapter(adapter);
        addImageAdapter = new AddImageAdapter(this, new ArrayList<>());
        binding.rclImage.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.rclImage.setAdapter(addImageAdapter);

        checkValidate();
        binding.buttonOK.setOnClickListener(view -> {
            if (adapter.getListChoose().size() == 0) {
                showMessage("Bạn chưa chọn đề xuất đánh giá");
            } else {
                if (infoItem == null) return;
                String gg = "";
                showProgressDialog(true);
                EvaluateCondition condition = new EvaluateCondition();
                condition.setSoSao(Integer.valueOf(sao));
                condition.setSoCT(infoItem.getSoCt());
                condition.setEmployeeID(infoItem.getMSNguoigiao());
                condition.setBinhLuan(binding.inputComment.getText().toString());
                condition.setNguoiDungMobileID( PublicVariables.UserInfo.getNguoiDungMobileID());
                condition.setListLyDoDanhGiaSaoo(adapter.getListChoose());
                for (String string : listImageString) {
                    gg = string;
                }
                if (gg.length() > 0) {
                    condition.setHinhAnh(gg);
                }
                viewModel.InsertVote(condition);
            }
        });
//        setRating();

        binding.textChooseImage.setOnClickListener(v -> showChooseFile(true));
        binding.rating.setRating(5);

        binding.rating.setOnRatingBarChangeListener((ratingBar, v, b) -> {
            ratingBar.setRating(v);
            if (v == 0) {
                showMessage("Bạn phải chọn số sao");
            } else {
                sao = String.valueOf(v);
                viewModel.GetListVote(String.valueOf(v));
            }
        });

        setAdapterClick();
    }

    @Override
    protected void onObserver() {
        super.onObserver();
        viewModel.itemList.observe(this, result -> {
            if (result!=null){
                adapter.clear();
                adapter.addAll(result);
            }
            showProgressDialog(false);

        });

        viewModel.itemMain.observe(this, condition -> {
            showToast("Thêm đánh giá thành công");
            showProgressDialog(false);
            finish();
        });
    }

    @SuppressLint("NonConstantResourceId")
    private void setAdapterClick() {
        addImageAdapter.setOnClickListener((view, position) -> {
            switch (view.getId()) {
                case R.id.imageDelete:
                    alertDialog("Xóa hình ảnh", "Bạn chắc chắn xóa hình ảnh này?", null, "ok", (dialogInterface, i) -> {
                        listImageString.remove(addImageAdapter.getItem(position));
                        addImageAdapter.removeItem(position);
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
        });
    }

    public void ResultImageBitMap(Bitmap bitmap) {
        if (bitmap == null) return;
        String gg;
        gg = AppUtils.formatBitMapToString(bitmap);
        if (gg.equals("")) return;
        listImageString.clear();
        listImageString.add(gg);
        addImageAdapter.clear();
        addImageAdapter.add(gg);
    }

    private void CheckCamera() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            checkSelfPermission(permissionsRequired);
        } else {
//            showChooseFile(true);
        }
    }

//    private void setRating() {
//        binding.rating.setOnRatingBarChangeListener((ratingBar, v, b) -> {
//            binding.rating.setRating(v);
//            if (v == 0) {
//                showMessage("Bạn phải chọn số sao");
//            } else {
//                presenter.GetListVote(String.valueOf(v));
//            }
//        });
//    }

    public void checkValidate() {
        if (isCheck) {
            binding.buttonOK.setEnabled(true);
            binding.buttonOK.setTextColor(getResources().getColor(R.color.White));
            binding.buttonOK.setBackgroundResource(R.drawable.bg_button_dark_no_radius);
        }
    }

    private void setHeader() {
        binding.layoutHeader.imageBack.setOnClickListener(view -> finish());
        binding.layoutHeader.textTitle.setText(getText(R.string.title_vote_employee));
    }

    @Override
    protected void initData() {
        infoItem = PublicVariables.infoBooking;
        viewModel.GetListVote("5");

        if (infoItem == null) return;
        binding.textNV.setText(infoItem.TenNguoiGiao);
    }

    @Override
    public void onClick(View view) {

    }
}
