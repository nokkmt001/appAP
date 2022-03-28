package com.anphat.supplier.data.entities;

import java.io.Serializable;

public class CategoryNew implements Serializable {
    public Float id;
    public Float parent_id;
    public String title;
    public String slug;
    public String photo = null;
    public Float level;
}
