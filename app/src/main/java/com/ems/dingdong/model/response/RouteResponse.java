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
}
