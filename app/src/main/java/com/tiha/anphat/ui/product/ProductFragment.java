package com.tiha.anphat.ui.product;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.tiha.anphat.R;
import com.tiha.anphat.data.entities.CategoryInfo;
import com.tiha.anphat.ui.base.BaseFragment;
import com.tiha.anphat.ui.product.detail.DetailProductFragment;
import com.tiha.anphat.utils.PublicVariables;

import java.util.List;

public class ProductFragment extends BaseFragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    PagerAdapter adapterPager;
//    OnCallbackReceived mCallback;
// Implement this interface in your Activity.
//    public interface OnCallbackReceived {
//        public void Update();
//    }
    List<CategoryInfo> list;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_product;
    }

    @Override
    protected void initView(View view) {
        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);

    }

    @Override
    protected void initData() {
        list = PublicVariables.listCategory;
        setupViewPager();
    }

    @Override
    protected void configToolbar() {

    }

    @Override
    public void onClick(View view) {

    }

    public void setupViewPager(){
        int tabCount = 2;
        viewPager.setOffscreenPageLimit(tabCount);
        adapterPager = new PagerAdapter(getActivity(),getChildFragmentManager(), tabCount);
        int position = 0;
        for (CategoryInfo item:list){
            adapterPager.addFragment(new DetailProductFragment(item.getCategory_ID()),item.getLoaihang(),position);
            position+=1;
        }
//        adapterPager.addFragment(new DetailProductFragment("PHUKIEN"),getString(R.string.accessory),0);
//        adapterPager.addFragment(new DetailProductFragment("BRN_2715GN"),getString(R.string.kitchen),1);
//        adapterPager.addFragment(new DetailProductFragment("PCCC"),getString(R.string.fire_fighting),2);
//        adapterPager.addFragment(new DetailProductFragment("VO"),getString(R.string.bark),3);
//        adapterPager.addFragment(new DetailProductFragment("BBINH"),getString(R.string.vase_set),4);
//        adapterPager.addFragment(new DetailProductFragment("GAO"),getString(R.string.rice),5);
//        adapterPager.addFragment(new DetailProductFragment(getString(R.string.gas)),getString(R.string.gas),6);
////        adapterPager.addFragment(new DetailProductFragment("LUA"),getString(R.string.paddy),7);
////        adapterPager.addFragment(new DetailProductFragment("NEP"),"NẾP",8);
////        adapterPager.addFragment(new DetailProductFragment("TAM"),"TẮM",9);
////        adapterPager.addFragment(new DetailProductFragment("TA"),"THỨC ĂN",10);
//        adapterPager.addFragment(new DetailProductFragment("KHUYENMAI"),getString(R.string.promotion),7);
        viewPager.setAdapter(adapterPager);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }
}
