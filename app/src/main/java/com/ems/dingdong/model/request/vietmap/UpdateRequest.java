package com.ems.dingdong.model.request.vietmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateRequest {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("idUser")
    @Expose
    private String idUser;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("layer")
    @Expose
    private String layer;

    public String getLayer() {
        return layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
