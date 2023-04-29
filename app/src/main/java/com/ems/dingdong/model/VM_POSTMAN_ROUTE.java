package com.ems.dingdong.model;

import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.modedata.VietMapOrderCreateBD13DataRequest;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VM_POSTMAN_ROUTE {
    /// Dữ liệu lộ trình
    /// </summary>
    @SerializedName("Data")
    @Expose
    private List<VietMapOrderCreateBD13DataRequest> vmOrderBd13DataRequest;
    @SerializedName("PostmanCode")
    @Expose
    private String PostmanCode;
    @SerializedName("DataType")
    @Expose
    private String DataType;

    public List<VietMapOrderCreateBD13DataRequest> getVmOrderBd13DataRequest() {
        return vmOrderBd13DataRequest;
    }

    public void setVmOrderBd13DataRequest(List<VietMapOrderCreateBD13DataRequest> vmOrderBd13DataRequest) {
        this.vmOrderBd13DataRequest = vmOrderBd13DataRequest;
    }

    public String getPostmanCode() {
        return PostmanCode;
    }

    public void setPostmanCode(String postmanCode) {
        PostmanCode = postmanCode;
    }

    public String getDataType() {
        return DataType;
    }

    public void setDataType(String dataType) {
        DataType = dataType;
    }


}
