package com.tiha.anphatsu.ui.custom;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;


import com.tiha.anphatsu.utils.AppUtils;

import java.util.Calendar;
import java.util.Date;


@SuppressLint("ValidFragment")
public class DateDialogAdapter extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    EditText txtDate;
    private String strDate;


    public DateDialogAdapter(View view, String _Date) {
        txtDate = (EditText) view;
        strDate = _Date;
    }

    public DateDialogAdapter() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        int year;
        int month;
        int day;
        if (strDate.equals("") || strDate == null) {
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        } else {
            Date date = AppUtils.formatStringToDateUtil(strDate, "dd/MM/yyyy");
            month = Integer.parseInt((String) android.text.format.DateFormat.format("MM", date)) - 1;
            year = Integer.parseInt((String) android.text.format.DateFormat.format("yyyy", date));
            day = Integer.parseInt((String) android.text.format.DateFormat.format("dd", date));

        }
//        int theme;
//        if (Build.VERSION.SDK_INT < 23)
//            theme = AlertDialog.THEME_TRADITIONAL;
//        else
//            theme = android.R.style.Widget_Material_DatePicker;
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {

        String _day = "";
        if (day < 10) {
            _day = "0" + day;
        } else {
            _day = "" + day;
        }
        String _month = "";
        if (month + 1 < 10) {
            _month = "0" + (month + 1);
        } else {
            _month = "" + (month + 1);
        }

        String date = _day + "/" + _month + "/" + year;
        txtDate.setText(date);

    }
}

