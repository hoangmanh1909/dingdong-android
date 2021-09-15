package com.ems.dingdong.callback;

import com.ems.dingdong.model.CallProvider;
import com.ems.dingdong.model.Item;

public interface CallProviderCallBack {
    void onCallProviderOptionResponse(Item callProvider, CallProvider itemCallProvider);
}
