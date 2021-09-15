package com.tiha.anphat.utils;

import android.graphics.Bitmap;
import android.os.CountDownTimer;

import com.tiha.anphat.data.entities.NewCustomer;
import com.tiha.anphat.data.entities.NguoiDungInfo;

public class PublicVariables {

    public static Bitmap BMPicture;
    public static String NgayLamViec;

    public static NewCustomer UserInfo;

    public static CountDownTimer countDownTimer;

    public static void ClearData() {
    }

//    public static List<MenuInfo> GetListMenuChildByParentID(String parentID) {
//        List<MenuInfo> list = new ArrayList<>();
//        int tabMenuID = -1;
//        try {
//            tabMenuID = Integer.parseInt(parentID);
//        } catch (NumberFormatException e) {
//            return list;
//        }
//        for (MenuInfo item : listAllMenu) {
//            if (item.getTabMenuID() == tabMenuID) {
//                list.add(item);
//            }
//        }
//        return list;
//    }
//
//    public static List<MenuInfo> GetListMenuParent() {
//        List<MenuInfo> list = new ArrayList<>();
//        List<String> listExist = new ArrayList<>();
//        for (MenuInfo item : listAllMenu) {
//            if (item.getTabMenuID() == -1) {
//                list.add(item);
//            }
//        }
//        return list;
//    }

}
