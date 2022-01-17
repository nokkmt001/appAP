package com.tiha.anphat.data.network.report;

import com.tiha.anphat.data.entities.condition.FilterReportCondition;
import com.tiha.anphat.data.entities.thongke.ThongkeChiInfo;

import java.util.List;

public interface IReportModel {
    void GetListSpend(FilterReportCondition condition, IGetListSpendFinish listener); // chi tiền

    interface IGetListSpendFinish {
        void onSuccess(List<ThongkeChiInfo> list);

        void onError(String error);
    }
}
