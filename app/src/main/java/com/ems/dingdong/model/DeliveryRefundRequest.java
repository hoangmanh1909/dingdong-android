package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class DeliveryRefundRequest {
    /// <summary>
    /// Bưu gửi chuyển hoàn
    /// </summary>
    @SerializedName("IsReturn")
    public boolean IsReturn;
    @SerializedName("Id")
    public Long Id;
    /// <summary>
    /// Phương thức vận chuyển
    /// 1 - Thủy bộ
    /// 2 - Máy bay
    /// </summary>
    @SerializedName("MethodReturn")
    public String MethodReturn;
    /// <summary>
    /// Ngày vận chuyển chuyển hoàn lại bưu gửi
    /// DD/MM/YYYY HH24:MI:SS
    /// </summary>
    @SerializedName("ReturnDate")
    public String ReturnDate;
    /// <summary>
    /// Tên người nhận chuyển hoàn
    /// </summary>
    @SerializedName("ReceiverNameReturn")
    public String ReceiverName;
    /// <summary>
    /// Địa chỉ người nhận chuyển hoàn
    /// </summary>
    @SerializedName("ReceiverAddressReturn")
    public String ReceiverAddress;
    /// <summary>
    /// Hướng chuyển hoàn
    /// 1 - Bưu cục gốc
    /// 2 - Địa chỉ người gửi
    /// 3 - Địa chỉ khác
    /// </summary>
    @SerializedName("AddressReturnType")
    public String AddressReturnType;
    /// <summary>
    /// Giá trị hướng chuyển hoàn
    /// 1 - Mã bc gốc - POAcceptedCode trong ds báo phát
    /// 2, 3 - Id tỉnh;Id huyện;Id xã (Danh mục hành chính)
    // ( = 2 chỉ hỗ trợ hiển thị sđt ng gửi ra thôi, chỉnh sửa ko có ý nghĩa, quan trong là vẫn phải chọn tỉnh huyện xã)
    /// </summary>
    @SerializedName("AddressReturn")
    public String AddressReturn;

    @SerializedName("ReturnReceiverTel")
    public String ReceiverTel;
    @SerializedName("NoteReturn")
    public String Note;


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }


    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getReceiverTel() {
        return ReceiverTel;
    }

    public void setReceiverTel(String receiverTel) {
        ReceiverTel = receiverTel;
    }

    public boolean isReturn() {
        return IsReturn;
    }

    public void setReturn(boolean aReturn) {
        IsReturn = aReturn;
    }

    public String getMethodReturn() {
        return MethodReturn;
    }

    public void setMethodReturn(String methodReturn) {
        MethodReturn = methodReturn;
    }

    public String getReturnDate() {
        return ReturnDate;
    }

    public void setReturnDate(String returnDate) {
        ReturnDate = returnDate;
    }

    public String getReceiverName() {
        return ReceiverName;
    }

    public void setReceiverName(String receiverName) {
        ReceiverName = receiverName;
    }

    public String getReceiverAddress() {
        return ReceiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        ReceiverAddress = receiverAddress;
    }

    public String getAddressReturnType() {
        return AddressReturnType;
    }

    public void setAddressReturnType(String addressReturnType) {
        AddressReturnType = addressReturnType;
    }

    public String getAddressReturn() {
        return AddressReturn;
    }

    public void setAddressReturn(String addressReturn) {
        AddressReturn = addressReturn;
    }
//
//    public String getGroupPACode() {
//        return GroupPACode;
//    }
//
//    public void setGroupPACode(String groupPACode) {
//        GroupPACode = groupPACode;
//    }
//
//    public String getPACode() {
//        return PACode;
//    }
//
//    public void setPACode(String PACode) {
//        this.PACode = PACode;
//    }
//
//    public String getVATCode() {
//        return VATCode;
//    }
//
//    public void setVATCode(String VATCode) {
//        this.VATCode = VATCode;
//    }
//
//    public long getFeeExVAT() {
//        return FeeExVAT;
//    }
//
//    public void setFeeExVAT(long feeExVAT) {
//        FeeExVAT = feeExVAT;
//    }
//
//    public long getFeeVat() {
//        return FeeVat;
//    }
//
//    public void setFeeVat(long feeVat) {
//        FeeVat = feeVat;
//    }
//
//    public long getTotalFee() {
//        return TotalFee;
//    }
//
//    public void setTotalFee(long totalFee) {
//        TotalFee = totalFee;
//    }
}