package com.tiha.anphat.utils;

import android.os.CountDownTimer;

import com.tiha.anphat.data.entities.CategoryInfo;
import com.tiha.anphat.data.entities.NewCustomer;
import com.tiha.anphat.data.entities.ProductInfo;
import com.tiha.anphat.data.entities.kho.KhoInfo;
import com.tiha.anphat.data.entities.order.BookingInfo;

import java.util.ArrayList;
import java.util.List;

public class PublicVariables {
    public static NewCustomer UserInfo;

    public static List<ProductInfo> listAllProDuct = new ArrayList<>();

    public static List<KhoInfo> listKho = new ArrayList<>();  // CTY

    public static CountDownTimer countDownTimer;

    public static KhoInfo khoInfoNear = null;

    public static List<CategoryInfo> listCategory = new ArrayList<>();

    public static ArrayList<String> listImageVote = new ArrayList<>();

    public static BookingInfo infoBooking = null;

    public static void ClearData() {
        UserInfo = new NewCustomer();
        listCategory = new ArrayList<>();
        listKho = new ArrayList<>();
        listImageVote = new ArrayList<>();
        listAllProDuct = new ArrayList<>();
        khoInfoNear = null;
    }


}
