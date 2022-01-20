package com.tiha.anphat.ui.home.choose;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.tiha.anphat.R;
import com.tiha.anphat.data.entities.CategoryInfo;
import com.tiha.anphat.databinding.ActivityCategoryFullBinding;
import com.tiha.anphat.ui.base.BaseTestActivity;
import com.tiha.anphat.ui.home.CategoryAddAdapter;
import com.tiha.anphat.ui.home.CategoryFullAdapter;
import com.tiha.anphat.utils.PublicVariables;

import java.util.ArrayList;
import java.util.List;

public class FullCategoryActivity extends BaseTestActivity<ActivityCategoryFullBinding> {
    ActivityCategoryFullBinding bd;
    CategoryFullAdapter adapterFullCategory;
    CategoryAddAdapter addAdapterShow;
    List<CategoryInfo> listMain = new ArrayList<>();

    @Override
    public ActivityCategoryFullBinding getViewBinding() {
        return bd = ActivityCategoryFullBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        addAdapterShow = new CategoryAddAdapter();
        bd.layoutHeader.textTitle.setText(R.string.category_title_main);
        bd.layoutHeader.imageBack.setOnClickListener((View.OnClickListener) v -> finish());
        adapterFullCategory = new CategoryFullAdapter();
        bd.rcl.setLayoutManager(new GridLayoutManager(this, 4));
        bd.rcl.setAdapter(addAdapterShow);
        adapterFullCategory.clear();
        setAdapterMainCategory();
        bd.rclFull.setLayoutManager(new GridLayoutManager(this, 4));
        bd.rclFull.setAdapter(adapterFullCategory);
        bd.layoutHeader.textSave.setOnClickListener(v -> {
            PublicVariables.listShowCategory = addAdapterShow.getListAllData();
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
        });
    }

    public void setAdapterMainCategory() {
        adapterFullCategory.setOnClickListener((view, position) -> {
            CategoryInfo item = adapterFullCategory.getItem(position);
            if (view.getId() == R.id.imageAddMain){
                if (addAdapterShow.getItemCount() == 8) {
                    showToast("Chỉ có thể sắp xếp tối đa 8 danh mục");
                } else {
                    item.setCheck(true);
                    addAdapterShow.add(item, addAdapterShow.getItemCount());
                    item.setCheck(true);
                    adapterFullCategory.update(item, true);
                }
            }

        });

        addAdapterShow.setOnClickListener((view, position) -> {
            CategoryInfo item = addAdapterShow.getItem(position);
            if (addAdapterShow.getItemCount() == 1) {
                showToast("bạn phải chọn ít nhất 1");
            } else {
                item.setCheck(false);
                adapterFullCategory.update(item,false);
                addAdapterShow.remove(position);
            }
        });
    }

    @Override
    protected void initData() {
        addAdapterShow.clear();
        addAdapterShow.addAll(PublicVariables.listShowCategory);

        listMain = PublicVariables.listCategory;
        for (CategoryInfo itemMain : listMain) {
            for (CategoryInfo item : PublicVariables.listShowCategory) {
                if (itemMain.getCategory_ID().equals(item.getCategory_ID())) {
                    itemMain.setCheck(true);
                }
            }
        }
        adapterFullCategory.addAll(listMain);
    }

    @Override
    public void onClick(View v) {

    }

}
