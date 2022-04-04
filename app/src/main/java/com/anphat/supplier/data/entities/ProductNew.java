package com.anphat.supplier.data.entities;

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

   public List<ProductNew> getProducts() {
      return products;
   }

}
