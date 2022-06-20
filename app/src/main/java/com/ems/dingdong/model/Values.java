package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Values {

    @SerializedName("DataRevert")
    private DataRevertModel DataRevert;

    @SerializedName("DataCate")
    public List<DataCateModel> dataCateModels;

    public List<DataCateModel> getDataCateModels() {
        return dataCateModels;
    }

    public void setDataCateModels(List<DataCateModel> dataCateModels) {
        this.dataCateModels = dataCateModels;
    }

    public DataRevertModel getDataRevert() {
        return DataRevert;
    }

    public void setDataRevert(DataRevertModel dataRevert) {
        DataRevert = dataRevert;
    }
}
