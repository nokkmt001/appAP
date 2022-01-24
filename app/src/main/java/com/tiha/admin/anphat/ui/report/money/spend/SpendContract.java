package com.tiha.admin.anphat.ui.report.money.spend;

import com.tiha.admin.anphat.data.entities.condition.FilterReportCondition;
import com.tiha.admin.anphat.data.entities.thongke.ThongkeChiInfo;

import java.util.List;

public interface SpendContract {
    interface View{
        void onSuccess(List<ThongkeChiInfo> list);
        void onError(String error);

    }
    interface Presenter{
        void GetListSpend(FilterReportCondition condition);
    }
}
