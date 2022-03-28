package com.anphat.supplier.ui.pay.history.vote;

import com.anphat.supplier.data.entities.ReasonEvaluate;
import com.anphat.supplier.data.entities.condition.EvaluateCondition;

import java.util.List;

public interface VoteContract {
    interface View {
        void onGetListVoteSuccess(List<ReasonEvaluate> list);

        void onGetListVoteError(String error);

        void onInsertVoteSuccess();

        void onInsertVoteError(String error);
    }

    interface Presenter {
        void GetListVote(String ID);

        void InsertVote(EvaluateCondition condition);
    }
}
