package com.ems.dingdong.model.request.vietmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Feature {
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("properties")
    @Expose
    private Object properties;
    @SerializedName("geometry")
    @Expose
    private Geometry geometry;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getProperties() {
        return properties;
    }

    public void setProperties(Object properties) {
        this.properties = properties;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }
}
