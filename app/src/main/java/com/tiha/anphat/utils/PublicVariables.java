package com.tiha.anphat.utils;

import android.graphics.Bitmap;
import android.os.CountDownTimer;

import com.tiha.anphat.data.entities.CategoryInfo;
import com.tiha.anphat.data.entities.NewCustomer;
import com.tiha.anphat.data.entities.NguoiDungInfo;
import com.tiha.anphat.data.entities.ProductInfo;
import com.tiha.anphat.data.entities.kho.KhoInfo;

import java.util.ArrayList;
import java.util.List;

public class PublicVariables {

    public static Bitmap BMPicture;
    public static String NgayLamViec;

    public static NewCustomer UserInfo;

    public static List<ProductInfo> listAllProDuct = new ArrayList<>();

    public static List<KhoInfo> listKho = new ArrayList<>();  // CTY

    public static CountDownTimer countDownTimer;

    public static List<CategoryInfo> listCategory = new ArrayList<>();

    public static void ClearData() {
        UserInfo = new NewCustomer();
    }


}
