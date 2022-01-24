package com.tiha.admin.anphat.ui.report.money.spend;

import com.tiha.admin.anphat.data.entities.condition.FilterReportCondition;
import com.tiha.admin.anphat.data.entities.thongke.ThongkeChiInfo;
import com.tiha.admin.anphat.data.network.report.IReportModel;
import com.tiha.admin.anphat.data.network.report.ReportModel;

import java.util.List;

public class SpendPresenter implements SpendContract.Presenter {
    ReportModel model;
    SpendContract.View view;

    public SpendPresenter(SpendContract.View view) {
        this.model = new ReportModel();
        this.view = view;
    }

    @Override
    public void GetListSpend(FilterReportCondition condition) {
        model.GetListSpend(condition, new IReportModel.IGetListSpendFinish() {
            @Override
            public void onSuccess(List<ThongkeChiInfo> list) {
                view.onSuccess(list);
            }

            @Override
            public void onError(String error) {
                view.onError(error);
            }
        });
    }
}
