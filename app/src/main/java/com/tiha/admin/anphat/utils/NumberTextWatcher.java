package com.tiha.admin.anphat.utils;

import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.ParseException;

public class NumberTextWatcher implements TextWatcher {

    public interface TextChangedListener {
        void onTextChanged(CharSequence s, int start, int before, int count, double value);
    }

    //
    private DecimalFormat dfFractional;
    private DecimalFormat decimalFormat;
    private boolean hasFractionalPart;

    private EditText et;

    TextChangedListener textChangedListener;

    public NumberTextWatcher(EditText et, TextChangedListener listener, String dfFractionalType) {
        this.et = et;
        et.setFilters(new InputFilter[]{GetFilterEditText(et)});
        dfFractional = AppUtils.formatNumber(dfFractionalType);
        //DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance(new Locale("vi", "VN"));//new DecimalFormatSymbols();
        //dfFractional = new DecimalFormat("###,###,##0.##", dfs);
        dfFractional.setDecimalSeparatorAlwaysShown(true);
        decimalFormat = AppUtils.formatNumber("N0");
        //decimalFormat = new DecimalFormat("#,###", dfs);

        hasFractionalPart = false;
        this.textChangedListener = listener;
    }

    private InputFilter GetFilterEditText(final EditText et) {
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                if ((source.equals(".") || source.equals(","))) {
                    et.setText(et.getText().toString() + ",");
                    et.setSelection(et.getText().length());
                }
                return source;
            }
        };
        return filter;
    }

    @SuppressWarnings("unused")
    private static final String TAG = "NumberTextWatcher";

    public void afterTextChanged(Editable s) {
        et.removeTextChangedListener(this);
        try {
            int inilen, endlen;
            inilen = et.getText().length();

//            String v = s.toString().replace(String.valueOf(dfFractional.getDecimalFormatSymbols().getGroupingSeparator()), "");
            String v = s.toString();
            if (!TextUtils.isEmpty(s) && s.toString().substring(s.toString().length() - 1).contains(".")) {
                v = s.toString().replace(".", ",");
            }
            Number n = dfFractional.parse(v);
            int cp = et.getSelectionStart();
            if (hasFractionalPart) {
                et.setText(dfFractional.format(n));
            } else {
                et.setText(decimalFormat.format(n));
            }
            endlen = et.getText().length();
            int sel = (cp + (endlen - inilen));
            if (sel > 0 && sel <= et.getText().length()) {
                et.setSelection(sel);
            } else {
                // place cursor at the end?
                et.setSelection(et.getText().length() - 1);
            }
        } catch (NumberFormatException nfe) {
            // do nothing?
        } catch (ParseException e) {
            // do nothing?
        }
        et.addTextChangedListener(this);


    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!TextUtils.isEmpty(s) && s.toString().substring(s.toString().length() - 1).contains(".")) {
            s = s.toString().replace(".", ",");
        }
        if (s.toString().contains(String.valueOf(dfFractional.getDecimalFormatSymbols().getDecimalSeparator()))) {
            hasFractionalPart = true;
        } else {
            hasFractionalPart = false;
        }
        double value = 0;
        try {
            Number n = dfFractional.parse(s.toString());
            value = n.doubleValue();
        } catch (ParseException e) {
        }
        textChangedListener.onTextChanged(s, start, before, count, value);
    }
}
