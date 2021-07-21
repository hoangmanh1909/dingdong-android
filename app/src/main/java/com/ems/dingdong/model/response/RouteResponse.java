package com.ems.dingdong.model.response;

import com.google.gson.annotations.SerializedName;

public class RouteResponse {
    @SerializedName("Id")
    Integer id;
    @SerializedName("LadingCode")
    String ladingCode;

    @SerializedName("FromRouteId")
    Integer fromRouteId;
    @SerializedName("FromRouteName")
    String fromRouteName;
    @SerializedName("FromPostmanId")
    Integer fromPostmanId;
    @SerializedName("FromPostmanName")
    String fromPostmanName;

    @SerializedName("ToRouteId")
    Integer toRouteId;
    @SerializedName("ToRouteName")
    String toRouteName;
    @SerializedName("ToPostmanId")
    Integer toPostmanId;
    @SerializedName("ToPostmanName")
    String toPostmanName;

    @SerializedName("StatusCode")
    String statusCode;
    @SerializedName("StatusName")
    String statusName;

    @SerializedName("StatusDate")
    String statusDate;
    @SerializedName("CODAmount")
    Integer codAmount;
    @SerializedName("Fee")
    Integer fee;



    @SerializedName("ToPOCode")
    String ToPOCode;
    @SerializedName("ToPOName")
    String ToPOName;

    public String getToPOCode() {
        return ToPOCode;
    }

    public void setToPOCode(String toPOCode) {
        ToPOCode = toPOCode;
    }

    public String getToPOName() {
        return ToPOName;
    }

    public void setToPOName(String toPOName) {
        ToPOName = toPOName;
    }

    public Integer getId() {
        return id;
    }

    public String getLadingCode() {
        return ladingCode;
    }

    public Integer getFromRouteId() {
        return fromRouteId;
    }

    public String getFromRouteName() {
        return fromRouteName;
    }

    public Integer getFromPostmanId() {
        return fromPostmanId;
    }

    public String getFromPostmanName() {
        return fromPostmanName;
    }

    public Integer getToRouteId() {
        return toRouteId;
    }

    public String getToRouteName() {
        return toRouteName;
    }

    public Integer getToPostmanId() {
        return toPostmanId;
    }

    public String getToPostmanName() {
        return toPostmanName;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public Integer getCodAmount() {
        return codAmount;
    }

    public Integer getFee() {
        return fee;
    }

    public String getStatusDate() {
        return statusDate;
    }
}
