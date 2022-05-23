package com.anphat.supplier.ui.base;

import android.text.Editable;
import android.text.TextWatcher;

public abstract class SearchMain implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {}
    @Override
    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
        onTextChanged(s.toString());
    }
    @Override
    public void afterTextChanged(Editable s) {
        onAfterChanged(s.toString());
    }
    protected void onTextChanged(String before) {}

    protected abstract void onAfterChanged(String text);
}
