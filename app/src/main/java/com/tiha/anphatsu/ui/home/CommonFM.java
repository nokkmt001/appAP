package com.tiha.anphatsu.ui.home;

import android.annotation.SuppressLint;

import androidx.fragment.app.Fragment;

import com.tiha.anphatsu.ui.category.DetailCategoryFragment;
import com.tiha.anphatsu.ui.product.full.ChooseProductFragment;

public class CommonFM {

   @SuppressLint("StaticFieldLeak")
   public static HomeFragment fragment  = new HomeFragment();

   public static ChooseProductFragment fragmentTwo  = null;

   public static DetailCategoryFragment fragmentThree  = null;

   public static void setFragment(HomeFragment fg){
      fragment = fg;
   }

   public static Fragment getFragment(){
      return fragment;
   }

}
