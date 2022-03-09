package com.tiha.anphatsu.ui.home;

import com.tiha.anphatsu.data.entities.ProductNew;

import java.util.ArrayList;
import java.util.List;

public class DataFilterProduct {

    public static List<ProductNew> list = new ArrayList<>();

    public static List<ProductNew> getList(Float category) {
        List<ProductNew> listAdd = new ArrayList<>();
        if (list.size() == 0) return null;
        for (ProductNew item : list) {
            if (item.category_id == category) {
                listAdd.add(item);
            }
        }
        return listAdd;
    }
}
