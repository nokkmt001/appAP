package com.tiha.anphatsu.ui.pay.history.vote;

import com.tiha.anphatsu.data.entities.ReasonEvaluate;
import com.tiha.anphatsu.data.entities.condition.EvaluateCondition;

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
