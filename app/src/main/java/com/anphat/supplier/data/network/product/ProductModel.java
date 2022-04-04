package com.anphat.supplier.data.network.product;

import androidx.annotation.NonNull;

import com.android.volley.VolleyError;
import com.anphat.supplier.data.entities.CategoryInfo;
import com.anphat.supplier.data.entities.ProductInfo;
import com.anphat.supplier.data.entities.ProductNew;
import com.anphat.supplier.data.entities.condition.InventoryCondition;
import com.anphat.supplier.data.entities.condition.ProductCondition;
import com.anphat.supplier.data.entities.condition.ProductPriceCondition;
import com.anphat.supplier.data.network.api.APIService;
import com.anphat.supplier.data.network.api.VolleyCallback;
import com.anphat.supplier.data.network.apiretrofit.API;
import com.anphat.supplier.data.network.apiretrofit.ResponseData;
import com.anphat.supplier.data.network.apiretrofit.RetrofitTest;
import com.anphat.supplier.utils.AppConstants;
import com.anphat.supplier.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductModel implements IProductModel {
    APIService service;
    API sv = RetrofitTest.createService(API.class);

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

    @Override
    public void GetProductNew(String ID, IGetProductNewFinish listener) {
        sv.GetProduct(ID).enqueue(new Callback<ResponseData<ProductNew>>() {
            @Override
            public void onResponse(@NonNull Call<ResponseData<ProductNew>> call, @NonNull Response<ResponseData<ProductNew>> response) {
                try {
                    ResponseData<ProductNew> result = response.body();
                    assert result != null;
                    listener.onSuccess(result.data);
                } catch (Exception e){
                    listener.onError(e.getMessage());
                }

            }

            @Override
            public void onFailure(@NonNull Call<ResponseData<ProductNew>> call, @NonNull Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }
}