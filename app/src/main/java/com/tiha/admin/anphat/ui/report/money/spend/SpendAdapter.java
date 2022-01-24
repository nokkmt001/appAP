package com.tiha.admin.anphat.ui.report.money.spend;

import android.view.View;
import android.widget.TextView;

import com.tiha.admin.anphat.R;
import com.tiha.admin.anphat.data.entities.thongke.ThongkeChiInfo;
import com.tiha.admin.anphat.ui.base.BaseAdapter;

public class SpendAdapter extends BaseAdapter<ThongkeChiInfo> {
    TextView tvNgay,tvSoCT,tvQuy,tvLyDo,tvTenKhachHang,tvVeKhoan,tvSoTien,tvNguoiChiTien,tvNguoiNhanTien;
    @Override
    public int getLayoutId() {
        return R.layout.item_spend;
    }

    @Override
    public void bind(View view) {}

    @Override
    public void setupViews(View view, ThongkeChiInfo item, int position) {
        tvNguoiNhanTien = bind(view,R.id.tvNguoiNhanTien);
        tvTenKhachHang = bind(view,R.id.tvTenKhachHang);
        tvNguoiChiTien = bind(view,R.id.tvNguoiChiTien);
        tvVeKhoan = bind(view,R.id.tvVeKhoan);
        tvSoTien = bind(view,R.id.tvSoTien);
        tvLyDo = bind(view,R.id.tvLyDo);
        tvNgay = bind(view,R.id.tvNgay);
        tvSoCT = bind(view,R.id.tvSoCT);
        tvQuy = bind(view,R.id.tvQuy);
    }

    @Override
    public void setupActions(View itemView, ThongkeChiInfo item, int position) {

    }
}
