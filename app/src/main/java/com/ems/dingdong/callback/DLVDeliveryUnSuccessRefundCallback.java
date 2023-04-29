package com.ems.dingdong.callback;

import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.more.GroupServiceMode;
import com.ems.dingdong.model.DLVDeliveryUnSuccessRefundRequest;

public interface DLVDeliveryUnSuccessRefundCallback {
    void onClickItem(DLVDeliveryUnSuccessRefundRequest item);
}
