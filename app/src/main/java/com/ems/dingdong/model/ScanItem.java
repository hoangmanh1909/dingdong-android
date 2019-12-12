package com.ems.dingdong.model;

import androidx.annotation.Nullable;

public class ScanItem {
    String code;

    public ScanItem(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public int hashCode() {
        return this.code.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj != null) {
            return this.code.equals(((ScanItem) obj).code);
        } else return false;
    }
}
