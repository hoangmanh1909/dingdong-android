package com.ems.dingdong.functions.mainhome.address.danhbadichi.model;

import com.google.gson.annotations.SerializedName;

public class DICRouteAddressBookCreateRequest {
    //    public long ModifydBy { get; set; }
//    public long RouteId { get; set; }
//    public long ProvinceId { get; set; }
//    public long DistrictId { get; set; }
//    public long Ward { get; set; }
//    public string VPostCode { get; set; }
    @SerializedName("ModifydBy")
    long ModifydBy;
    @SerializedName("Id")
    long Id;
    @SerializedName("RouteId")
    long RouteId;
    @SerializedName("ProvinceId")
    long ProvinceId;
    @SerializedName("DistrictId")
    long DistrictId;
    @SerializedName("VPostCode")
    String VPostCode;
    @SerializedName("Ward")
    long Ward;

    /// <summary>
    /// Làng xóm
    /// </summary>
    @SerializedName("Village")
    String Village;
    /// <summary>
    /// Thôn ấp
    /// </summary>
    @SerializedName("SubVillage")
    String SubVillage;
    @SerializedName("StreetName")
    String StreetName;
    @SerializedName("HouseNumber")
    String HouseNumber;
    /// <summary>
    /// Ngõ
    /// </summary>
    @SerializedName("AlleyLV1")
    String AlleyLV1;
    /// <summary>
    /// Ngách
    /// </summary>
    @SerializedName("AlleyLV2")
    String AlleyLV2;
    /// <summary>
    /// Mã google
    /// </summary>
    @SerializedName("PlusCode")
    String PlusCode;


    /// <summary>
    /// Mã địa chỉ số
    /// </summary>
    @SerializedName("NdasCode")
    String NdasCode;
    @SerializedName("NameBuilding")
    String NameBuilding;
    @SerializedName("NameHouseArea")
    String NameHouseArea;
    @SerializedName("Longitude")
    String Longitude;
    @SerializedName("Latitude")
    String Latitude;
    @SerializedName("PlaceId")
    String PlaceId;
    @SerializedName("AddressName")
    String AddressName;
    @SerializedName("Verify")
    String Verify;
    @SerializedName("FormattedAddress")
    String FormattedAddress;
    @SerializedName("FullAddress")
    String FullAddress;
    @SerializedName("ProvinceName")
    String ProvinceName;
    @SerializedName("DistrictName")
    String DistrictName;
    @SerializedName("WardName")
    String WardName;
    /// <summary>
    /// Thông tin kèm theo
    /// </summary>
    @SerializedName("AddInfo")
    DICRouteAddressBookAddInfoCreateRequest AddInfo;

    public String getProvinceName() {
        return ProvinceName;
    }

    public void setProvinceName(String provinceName) {
        ProvinceName = provinceName;
    }

    public String getDistrictName() {
        return DistrictName;
    }

    public void setDistrictName(String districtName) {
        DistrictName = districtName;
    }

    public String getWardName() {
        return WardName;
    }

    public void setWardName(String wardName) {
        WardName = wardName;
    }

    public String getFormattedAddress() {
        return FormattedAddress;
    }

    public String getFullAddress() {
        return FullAddress;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public void setFullAddress(String fullAddress) {
        FullAddress = fullAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        FormattedAddress = formattedAddress;
    }

    public long getModifydBy() {
        return ModifydBy;
    }

    public void setModifydBy(long modifydBy) {
        ModifydBy = modifydBy;
    }

    public long getRouteId() {
        return RouteId;
    }

    public void setRouteId(long routeId) {
        RouteId = routeId;
    }

    public long getProvinceId() {
        return ProvinceId;
    }

    public void setProvinceId(long provinceId) {
        ProvinceId = provinceId;
    }

    public long getDistrictId() {
        return DistrictId;
    }

    public void setDistrictId(long districtId) {
        DistrictId = districtId;
    }

    public String getVPostCode() {
        return VPostCode;
    }

    public void setVPostCode(String VPostCode) {
        this.VPostCode = VPostCode;
    }

    public long getWard() {
        return Ward;
    }

    public void setWard(long ward) {
        Ward = ward;
    }

    public String getVillage() {
        return Village;
    }

    public void setVillage(String village) {
        Village = village;
    }

    public String getSubVillage() {
        return SubVillage;
    }

    public void setSubVillage(String subVillage) {
        SubVillage = subVillage;
    }

    public String getStreetName() {
        return StreetName;
    }

    public void setStreetName(String streetName) {
        StreetName = streetName;
    }

    public String getHouseNumber() {
        return HouseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        HouseNumber = houseNumber;
    }

    public String getAlleyLV1() {
        return AlleyLV1;
    }

    public void setAlleyLV1(String alleyLV1) {
        AlleyLV1 = alleyLV1;
    }

    public String getAlleyLV2() {
        return AlleyLV2;
    }

    public void setAlleyLV2(String alleyLV2) {
        AlleyLV2 = alleyLV2;
    }

    public String getPlusCode() {
        return PlusCode;
    }

    public void setPlusCode(String plusCode) {
        PlusCode = plusCode;
    }

    public String getNdasCode() {
        return NdasCode;
    }

    public void setNdasCode(String ndasCode) {
        NdasCode = ndasCode;
    }

    public String getNameBuilding() {
        return NameBuilding;
    }

    public void setNameBuilding(String nameBuilding) {
        NameBuilding = nameBuilding;
    }

    public String getNameHouseArea() {
        return NameHouseArea;
    }

    public void setNameHouseArea(String nameHouseArea) {
        NameHouseArea = nameHouseArea;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getPlaceId() {
        return PlaceId;
    }

    public void setPlaceId(String placeId) {
        PlaceId = placeId;
    }

    public String getAddressName() {
        return AddressName;
    }

    public void setAddressName(String addressName) {
        AddressName = addressName;
    }

    public String getVerify() {
        return Verify;
    }

    public void setVerify(String verify) {
        Verify = verify;
    }

    public DICRouteAddressBookAddInfoCreateRequest getAddInfo() {
        return AddInfo;
    }

    public void setAddInfo(DICRouteAddressBookAddInfoCreateRequest addInfo) {
        AddInfo = addInfo;
    }
}
