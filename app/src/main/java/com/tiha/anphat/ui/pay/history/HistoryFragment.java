package com.tiha.anphat.ui.pay.history;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.tiha.anphat.R;
import com.tiha.anphat.data.entities.HistoryBooking;
import com.tiha.anphat.ui.base.BaseEventClick;
import com.tiha.anphat.ui.base.BaseFragment;
import com.tiha.anphat.ui.pay.history.vote.VoteEmployeeActivity;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends BaseFragment implements HistoryBookingContract.View {
    HistoryBookingPresenter presenter;
    HistoryBookingAdapter adapter;
    RecyclerView rcl;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_history;
    }

    @Override
    protected void initView(View view) {
        adapter = new HistoryBookingAdapter(new ArrayList());
        rcl = bind(view,R.id.rcl);
        rcl.setAdapter(adapter);
        adapter.setOnClick(new BaseEventClick.OnClickListener() {
            @Override
            public void onClick(View view, int position) {
                switch (view.getId()){
                    case R.id.buttonVote:
                        startVote();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void startVote(){
        Intent intent= new Intent(getActivity(), VoteEmployeeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void initData() {
        presenter = new HistoryBookingPresenter(this);
        presenter.GetListHistoryBooking();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onGetListHistoryBookingSuccess(List<HistoryBooking> list) {
        if (list.size()==0){
            showNoResult();
        } else {
            adapter.clear();
            adapter.addAll(list);
        }
    }

    @Override
    public void onGetListHistoryBookingError(String error) {
        showMessage(error);
    }
}
