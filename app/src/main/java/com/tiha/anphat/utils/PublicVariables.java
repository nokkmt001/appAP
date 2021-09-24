package com.tiha.anphat.utils;

import android.graphics.Bitmap;
import android.os.CountDownTimer;

import com.tiha.anphat.data.entities.NewCustomer;
import com.tiha.anphat.data.entities.NguoiDungInfo;
import com.tiha.anphat.data.entities.ProductInfo;

import java.util.ArrayList;
import java.util.List;

public class PublicVariables {

    public static Bitmap BMPicture;
    public static String NgayLamViec;

    public static NewCustomer UserInfo;

    public static List<ProductInfo> listAllProDuct = new ArrayList<>();

    public static CountDownTimer countDownTimer;

    public static void ClearData() {
        UserInfo = new NewCustomer();
    }


}
