package com.tiha.anphatsu.utils;

import android.os.CountDownTimer;

import com.tiha.anphatsu.data.AppPreference;
import com.tiha.anphatsu.data.entities.CartInfo;
import com.tiha.anphatsu.data.entities.CategoryInfo;
import com.tiha.anphatsu.data.entities.CategoryNew;
import com.tiha.anphatsu.data.entities.NewCustomer;
import com.tiha.anphatsu.data.entities.ProductInfo;
import com.tiha.anphatsu.data.entities.ProductNew;
import com.tiha.anphatsu.data.entities.kho.KhoInfo;
import com.tiha.anphatsu.data.entities.order.BookingInfo;

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

    public static List<CartInfo> listBooking = null;

    public static List<ProductNew> listKM = new ArrayList<>();

    public static List<CategoryInfo> listShowCategory = new ArrayList<>();

    public static List<CategoryNew> listAllCategoryMain = AppPreference.getCategory();

    public static void ClearData() {
        UserInfo = new NewCustomer();
        listCategory = new ArrayList<>();
        listKho = new ArrayList<>();
        listImageVote = new ArrayList<>();
        listAllProDuct = new ArrayList<>();
        khoInfoNear = null;
        listBooking = null;
    }


}
