package com.tiha.anphat.data.network.product;

import com.android.volley.VolleyError;
import com.tiha.anphat.data.entities.CategoryInfo;
import com.tiha.anphat.data.entities.ProductInfo;
import com.tiha.anphat.data.entities.condition.InventoryCondition;
import com.tiha.anphat.data.entities.condition.ProductCondition;
import com.tiha.anphat.data.entities.condition.ProductPriceCondition;
import com.tiha.anphat.data.network.api.APIService;
import com.tiha.anphat.data.network.api.VolleyCallback;
import com.tiha.anphat.utils.AppConstants;
import com.tiha.anphat.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ProductModel implements IProductModel {
    APIService service;

    @Override
    public void GetListAllProduct(final IGetListAllProductFinishListener listener) {
        String URL = AppConstants.URL_GET_LIST_All_PRODUCT;
        Map<String, String> params = new HashMap<>();
        params.put("UserName", "TIHA");
        service = new APIService(URL);
        service.DownloadJsonPOST(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                listener.onSuccess(new ProductInfo().getListAllProduct(response));
            }

            @Override
            public void onError(VolleyError error) {
                listener.onError(AppUtils.getMessageVolleyError(error));
            }
        }, params);
    }

    @Override
    public void GetListProduct(ProductCondition condition, final IGetListProductFinishListener listener) {
        String URL = AppConstants.URL_GET_LIST_PRODUCT;
        Map<String, String> params = new HashMap<>();
        params.put("Begin", condition.getBegin().toString());
        params.put("End", condition.getEnd().toString());
        params.put("NhomLoaiHang", condition.getNhomLoaiHang());
        params.put("UserName", condition.getUserName());
        params.put("TextSearch", condition.getTextSearch());
        service = new APIService(URL);
        service.DownloadJsonPOST(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String jsonList = jsonObject.getJSONArray("List").toString();
                    Integer counter = Integer.parseInt(jsonObject.get("Total").toString());
                    listener.onSuccess(new ProductInfo().getListAllProduct(jsonList), counter);
                } catch (JSONException e) {
                    listener.onError(e.getMessage());
                }
            }

            @Override
            public void onError(VolleyError error) {
                listener.onError(AppUtils.getMessageVolleyError(error));
            }
        }, params);
    }

    @Override
    public void GetImageFromProductID(String productID, final IGetImageFromProductIDFinishListener listener) {
        String URL = MessageFormat.format(AppConstants.URL_GET_IMAGE_PRODUCT, productID);
        service = new APIService(URL);
        service.DownloadJson(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                listener.onSuccess(response);
            }

            @Override
            public void onError(VolleyError error) {
                listener.onError(AppUtils.getMessageVolleyError(error));
            }
        });
    }

    @Override
    public void GetProductPriceByUserID(ProductPriceCondition condition, final IGetProductPriceByUserIDListener listener) {
        String URL = AppConstants.URL_GET_PRICE_PRODUCT;
        service = new APIService(URL);
        Map<String, String> params = new HashMap<>();
        params.put("NguoiDungMobileID", condition.getNguoiDungMobileID());
        params.put("ProductID", condition.getProductID());
        params.put("Ngay", condition.getNgay());
        params.put("SoLuong", condition.getSoLuong().toString());
        service.DownloadJsonPOST(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                listener.onSuccess(Double.parseDouble(response));
            }

            @Override
            public void onError(VolleyError error) {
                listener.onError(AppUtils.getMessageVolleyError(error));

            }
        }, params);
    }

    @Override
    public void GetProductInventory(String maKho, String productID, String date, final IGetProductInventoryFinish listener) {
        String URL = MessageFormat.format(AppConstants.URL_GET_PRODUCT_TON_KHO, maKho, productID, date);
        service = new APIService(URL);
        service.DownloadJson(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                listener.onSuccess(Double.valueOf(response));
            }

            @Override
            public void onError(VolleyError error) {
                listener.onError(AppUtils.getMessageVolleyError(error));
            }
        });
    }

    @Override
    public void GetListProductInventory(InventoryCondition condition, final IGetAllProductInventoryFinish listener) {
        String URL = AppConstants.URL_GetListTonKho;
        Map<String, String> params = new HashMap<>();
        params.put("loaiXem", "NO");
        params.put("listKho", "");
        params.put("denNgay", AppUtils.formatDateToString(Calendar.getInstance().getTime(),"yyyy-MM-dd'T'HH:mm:ss"));
        params.put("userName", "TIHA");
        params.put("listNhomHang", condition.getListNhomHang());
        params.put("tenHangSearch", condition.getTenHangSearch());
        service = new APIService(URL);
        service.DownloadJsonPOST(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                listener.onSuccess(new ProductInfo().getListAllProduct(response));
            }

            @Override
            public void onError(VolleyError error) {
                listener.onError(AppUtils.getMessageVolleyError(error));
            }
        }, params);
    }

    @Override
    public void GetListCategory(final IGetListCategoryFinish listener) {
        String URL = MessageFormat.format(AppConstants.URL_GET_CATEGORY, "TIHA");
        service = new APIService(URL);
        service.DownloadJson(new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                listener.onSuccess(new CategoryInfo().getListCategory(response) );
            }

            @Override
            public void onError(VolleyError error) {
                listener.onError(AppUtils.getMessageVolleyError(error));
            }
        });
    }
}
