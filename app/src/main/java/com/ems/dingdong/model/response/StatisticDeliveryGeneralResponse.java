package com.ems.dingdong.model.response;

import com.google.gson.annotations.SerializedName;

public class StatisticDeliveryGeneralResponse {
    @SerializedName("ServiceCode")
    private String serviceCode;
    @SerializedName("ServiceName")
    private String serviceName;
    @SerializedName("Quantity")
    private String quantity;
    @SerializedName("QuantityCOD")
    private String quantityCOD;
    @SerializedName("QuantityC")
    private String quantityC;
    @SerializedName("QuantityPPA")
    private String quantityPPA;

    public String getServiceCode() {
        return serviceCode;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getQuantityCOD() {
        return quantityCOD;
    }

    public String getQuantityC() {
        return quantityC;
    }

    public String getQuantityPPA() {
        return quantityPPA;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setQuantityCOD(String quantityCOD) {
        this.quantityCOD = quantityCOD;
    }

    public void setQuantityC(String quantityC) {
        this.quantityC = quantityC;
    }

    public void setQuantityPPA(String quantityPPA) {
        this.quantityPPA = quantityPPA;
    }
}
