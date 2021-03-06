package com.tiha.admin.anphat.data.network.evaluate;

import com.tiha.admin.anphat.data.entities.ReasonEvaluate;
import com.tiha.admin.anphat.data.entities.condition.EvaluateCondition;

import java.util.List;

public interface IEvaluateModel {
    void InsertEvaluate(EvaluateCondition condition, IInsertEvaluateFinish listener);

    interface IInsertEvaluateFinish {
        void onSuccess(EvaluateCondition info);

        void onError(String error);
    }

    void GetListReasonEvaluate(String ID, IGetListEvaluate listener);

    interface IGetListEvaluate {
        void onSuccess(List<ReasonEvaluate> list);

        void onError(String error);
    }
}
