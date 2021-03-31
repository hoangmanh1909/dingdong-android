package com.ems.dingdong.model.response;

import com.ems.dingdong.model.OrderChangeRouteModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderChangeRouteDingDongManagementResponse {
    @SerializedName("FromOrders")
    private List<OrderChangeRouteModel> fromOrders;

    @SerializedName("ToOrders")
    private List<OrderChangeRouteModel> toOrders;

    public List<OrderChangeRouteModel> getFromOrders() {
        return fromOrders;
    }

    public void setFromOrders(List<OrderChangeRouteModel> fromOrders) {
        this.fromOrders = fromOrders;
    }

    public List<OrderChangeRouteModel> getToOrders() {
        return toOrders;
    }

    public void setToOrders(List<OrderChangeRouteModel> toOrders) {
        this.toOrders = toOrders;
    }
}
