package com.anphat.supplier.data;

import com.anphat.supplier.data.entities.ProductNew;
import com.anphat.supplier.ui.base.ReturnData;

import java.util.ArrayList;
import java.util.List;

public class CommonData {

    public static Integer perPage = 30;

    public static Integer perPagePromotion = 30;

    public static List<ProductNew> data = new ArrayList();

    public static List<ProductNew> getDataInfo(Integer page, ReturnData returnData) {
        List<ProductNew> list = new ArrayList<>();
        int firstIndex = page == 1 ? 0 : (page - 1) * perPage;
        int lastIndex = page == 1 ? perPage - 1 : page * perPage - 1;

        for (int i = firstIndex; i < lastIndex; i++) {
            try {
                list.add(data.get(i));
            } catch (Exception e) {
                if (returnData!=null){
                    returnData.onResult();
                }
                return list;
            }
        }
        return list;
    }

    public static List<ProductNew> dataPromotions = new ArrayList();

    public static List<ProductNew> getDataPromotions(Integer page, ReturnData returnData) {
        List<ProductNew> list = new ArrayList<>();
        int firstIndex = page == 1 ? 0 : (page - 1) * perPagePromotion;
        int lastIndex = page == 1 ? perPagePromotion - 1 : page * perPagePromotion - 1;

        for (int i = firstIndex; i < lastIndex; i++) {
            try {
                list.add(dataPromotions.get(i));
            } catch (Exception e) {
                if (returnData!=null){
                    returnData.onResult();
                }
                return list;
            }
        }
        return list;
    }
}
