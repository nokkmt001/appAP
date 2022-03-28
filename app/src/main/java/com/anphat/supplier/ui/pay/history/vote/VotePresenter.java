package com.anphat.supplier.ui.pay.history.vote;

import com.anphat.supplier.data.entities.ReasonEvaluate;
import com.anphat.supplier.data.entities.condition.EvaluateCondition;
import com.anphat.supplier.data.network.evaluate.EvaluateModel;
import com.anphat.supplier.data.network.evaluate.IEvaluateModel;

import java.util.List;

public class VotePresenter implements VoteContract.Presenter {
    EvaluateModel model;
    VoteContract.View view;

    public VotePresenter(VoteContract.View view) {
        this.view = view;
        this.model = new EvaluateModel();
    }

    @Override
    public void GetListVote(String ID) {
        model.GetListReasonEvaluate(ID, new IEvaluateModel.IGetListEvaluate() {
            @Override
            public void onSuccess(List<ReasonEvaluate> list) {
                view.onGetListVoteSuccess(list);
            }

            @Override
            public void onError(String error) {
                view.onGetListVoteError(error);
            }
        });

    }

    @Override
    public void InsertVote(EvaluateCondition condition) {
        model.InsertEvaluate(condition, new IEvaluateModel.IInsertEvaluateFinish() {
            @Override
            public void onSuccess(EvaluateCondition info) {
                view.onInsertVoteSuccess();
            }

            @Override
            public void onError(String error) {
                view.onInsertVoteError(error);
            }
        });
    }
}
