package com.anphat.supplier.data.entities;

import com.anphat.supplier.data.entities.gift.GiftInfo;
import com.anphat.supplier.ui.product.full.GiftAdapter;

import java.util.List;

public class ProductNew {
   public float id;
   public String code;
   public float category_id;
   public String title;
   public String slug;
   public String photo;
   public Double price_original;
   public Double price;
   public Double discount;
   public String description;
   public String content;
   public List<ProductNew> products;

   public List<GiftInfo> gifts;

   public List<ProductNew> getProducts() {
      return products;
   }

   public List<GiftInfo> getGifts() {
      return gifts;
   }

}
