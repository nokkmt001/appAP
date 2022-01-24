package com.tiha.admin.anphat.data.network.report;

import com.tiha.admin.anphat.data.entities.condition.FilterReportCondition;
import com.tiha.admin.anphat.data.entities.thongke.ThongkeChiInfo;

import java.util.List;

public interface IReportModel {
    void GetListSpend(FilterReportCondition condition, IGetListSpendFinish listener); // chi tiền

    interface IGetListSpendFinish {
        void onSuccess(List<ThongkeChiInfo> list);

        void onError(String error);
    }
}
