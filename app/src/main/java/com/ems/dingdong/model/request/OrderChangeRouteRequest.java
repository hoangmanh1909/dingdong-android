package com.ems.dingdong.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderChangeRouteRequest {

    @SerializedName("OrderChangeRouteIds")
    public List<Long> orderChangeRouteIds;

    @SerializedName("PostmanId")
    public long postmanId;

    public List<Long> getOrderChangeRouteIds() {
        return orderChangeRouteIds;
    }

    public void setOrderChangeRouteIds(List<Long> orderChangeRouteIds) {
        this.orderChangeRouteIds = orderChangeRouteIds;
    }

    public long getPostmanId() {
        return postmanId;
    }

    public void setPostmanId(long postmanId) {
        this.postmanId = postmanId;
    }
}
