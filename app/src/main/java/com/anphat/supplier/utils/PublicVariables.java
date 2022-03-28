package com.anphat.supplier.utils;

import android.os.CountDownTimer;

import com.anphat.supplier.data.AppPreference;
import com.anphat.supplier.data.entities.CartInfo;
import com.anphat.supplier.data.entities.CategoryInfo;
import com.anphat.supplier.data.entities.CategoryNew;
import com.anphat.supplier.data.entities.NewCustomer;
import com.anphat.supplier.data.entities.ProductInfo;
import com.anphat.supplier.data.entities.ProductNew;
import com.anphat.supplier.data.entities.kho.KhoInfo;
import com.anphat.supplier.data.entities.order.BookingInfo;

import java.util.ArrayList;
import java.util.List;

public class PublicVariables {
    public static NewCustomer UserInfo = AppPreference.getUserMain();

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

    public static String token = "";
    public static BookingInfo itemBooking = null;

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
