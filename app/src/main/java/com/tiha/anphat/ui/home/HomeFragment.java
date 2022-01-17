package com.tiha.anphat.ui.home;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.tiha.anphat.R;
import com.tiha.anphat.ui.base.BaseFragment;

public class HomeFragment extends BaseFragment {
    EditText inputSearch;
    ImageView imageDelete;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        imageDelete =view.findViewById(R.id.imageDelete);
        inputSearch = view.findViewById(R.id.inputSearch);
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
              imageDelete.setVisibility(!s.toString().isEmpty()? View.VISIBLE:View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputSearch.setText("");
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {

    }
}
