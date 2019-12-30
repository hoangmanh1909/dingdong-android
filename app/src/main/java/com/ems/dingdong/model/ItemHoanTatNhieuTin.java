package com.ems.dingdong.model;

import com.ems.dingdong.utiles.Constants;

public class ItemHoanTatNhieuTin {
    String shipmentCode;
    int status = Constants.GREY;
    String employeeId;
    String orderPostmanId;
    String orderId;

    public ItemHoanTatNhieuTin(String shipmentCode, int status, String employeeId, String orderPostmanId, String orderId) {
        this.shipmentCode = shipmentCode;
        this.status = status;
        this.employeeId = employeeId;
        this.orderPostmanId = orderPostmanId;
        this.orderId = orderId;
    }

    public String getShipmentCode() {
        return shipmentCode;
    }

    public int getStatus() {
        return status;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getOrderPostmanId() {
        return orderPostmanId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
