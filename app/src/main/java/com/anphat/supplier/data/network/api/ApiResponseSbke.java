package com.anphat.supplier.data.network.api;

import java.util.List;

public class ApiResponseSbke<T> {
    public Integer Status;
    public String Message;
    public List<T> listData;
    public List<T> list;
    public T Data;
}
