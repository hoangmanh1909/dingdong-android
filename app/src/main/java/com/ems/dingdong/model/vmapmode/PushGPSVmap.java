package com.ems.dingdong.model.vmapmode;

import com.google.gson.annotations.SerializedName;

public class PushGPSVmap {
    @SerializedName("ts")
    long ts;
    @SerializedName("values")
    ValuesVmap values;

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public ValuesVmap getValues() {
        return values;
    }

    public void setValues(ValuesVmap values) {
        this.values = values;
    }
}
