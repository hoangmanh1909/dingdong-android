package com.ems.dingdong.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Bd13Create {
    @SerializedName("PostmanId")
    @Expose
    private Integer postmanId;
    @SerializedName("PoDeliveryCode")
    @Expose
    private String poDeliveryCode;
    @SerializedName("RouteDeliveryCode")
    @Expose
    private String routeDeliveryCode;
    @SerializedName("PostmanCode")
    @Expose
    private String postmanCode;
    @SerializedName("Ids")
    @Expose
    private List<Integer> ids;

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public Integer getPostmanId() {
        return postmanId;
    }

    public void setPostmanId(Integer postmanId) {
        this.postmanId = postmanId;
    }

    public String getPoDeliveryCode() {
        return poDeliveryCode;
    }

    public void setPoDeliveryCode(String poDeliveryCode) {
        this.poDeliveryCode = poDeliveryCode;
    }

    public String getRouteDeliveryCode() {
        return routeDeliveryCode;
    }

    public void setRouteDeliveryCode(String routeDeliveryCode) {
        this.routeDeliveryCode = routeDeliveryCode;
    }

    public String getPostmanCode() {
        return postmanCode;
    }

    public void setPostmanCode(String postmanCode) {
        this.postmanCode = postmanCode;
    }

}
