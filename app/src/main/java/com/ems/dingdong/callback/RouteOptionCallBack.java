package com.ems.dingdong.callback;

import com.ems.dingdong.model.Item;
import com.ems.dingdong.model.RouteInfo;

public interface RouteOptionCallBack {
    void onRouteOptionResponse(Item routeInfo, RouteInfo itemRouteInfo);
}
