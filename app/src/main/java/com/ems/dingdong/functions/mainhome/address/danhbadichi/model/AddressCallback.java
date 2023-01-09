package com.ems.dingdong.functions.mainhome.address.danhbadichi.model;

import com.ems.dingdong.model.AddressModel;

public interface AddressCallback {
    void onClickItem(VmapAddress item);

    void onAddDiachinguoisudung(DICRouteAddressBookAddInfoUserCreateRequest item);
}
