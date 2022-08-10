package com.ems.dingdong.model;

import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.more.LadingProduct;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeliveryPostman implements Comparable {
    @SerializedName("Count")
    @Expose
    private Integer count;
    @SerializedName("CuocCH")
    @Expose
    private String CuocCH;
    @SerializedName("Type")
    @Expose
    private Integer Type;
    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("MaE")
    @Expose
    private String maE;
    @SerializedName("SenderName")
    @Expose
    private String senderName;
    @SerializedName("SenderMobile")
    @Expose
    private String senderMobile;
    @SerializedName("ReferenceCode")
    @Expose
    private String ReferenceCode;
    @SerializedName("SenderAddress")
    @Expose
    private String senderAddress;
    @SerializedName("ReciverName")
    @Expose
    private String reciverName;
    @SerializedName("ReciverAddress")
    @Expose
    private String reciverAddress;
    @SerializedName("ReceiverEmail")
    @Expose
    private String receiverEmail;
    @SerializedName("ReciverMobile")
    @Expose
    private String reciverMobile;
    @SerializedName("PoCode")
    @Expose
    private String poCode;
    @SerializedName("Weight")
    @Expose
    private Integer weight;
    @SerializedName("CreateDate")
    @Expose
    private Integer createDate;
    @SerializedName("Route")
    @Expose
    private Integer route;
    @SerializedName("Order")
    @Expose
    private Integer order;
    @SerializedName("Amount")
    @Expose
    private Integer amount;
    @SerializedName("Service")
    @Expose
    private String service;
    @SerializedName("ServiceName")
    @Expose
    private String serviceName;
    @SerializedName("Info")
    @Expose
    private String info;
    @SerializedName("Note")
    @Expose
    private String note;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Instruction")
    @Expose
    private String instruction;
    @SerializedName("AutoCallStatus")
    @Expose
    private String autoCallStatus;
    @SerializedName("RouteId")
    @Expose
    private Integer routeId;
    @SerializedName("RouteCode")
    @Expose
    private String routeCode;
    @SerializedName("ShiftId")
    @Expose
    private Integer shiftId;
    @SerializedName("TotalFee")
    @Expose
    private Integer totalFee;
    @SerializedName("NewInstruction")
    @Expose
    private String newInstruction;
    @SerializedName("BatchCode")
    @Expose
    private String batchCode;
    @SerializedName("VATCode")
    @Expose
    private String vatCode;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("NewReceiverAddress")
    @Expose
    private PointMyVNPost newReceiverAddress;
    @SerializedName("BD13CreatedDate")
    @Expose
    private String bD13CreatedDate;
    @SerializedName("IsPA")
    @Expose
    private String isPA;
    @SerializedName("IsItemReturn")
    @Expose
    private String isItemReturn;
    @SerializedName("OrderNumberInBD13")
    @Expose
    private Integer orderNumberInBD13;
    @SerializedName("AmountForBatch")
    @Expose
    private String amountForBatch;
    @SerializedName("ItemsInBatch")
    @Expose
    private Integer itemsInBatch;
    @SerializedName("ReplaceCode")
    private String replaceCode;
    @SerializedName("IsPaymentBatch")
    private boolean isPaymentBatch;
    @SerializedName("LastLadingCode")
    private String lastLadingCode;
    @SerializedName("IsRePaymentBatch")
    private boolean isRePaymentBatch;
    @SerializedName("CustomerCode")
    private String customerCode;
    @SerializedName("AuthenType")
    private Integer authenType;
    @SerializedName("Products")
    private List<ProductModel> listProducts;
    @SerializedName("SMLStatusName")
    private String sMLStatusName;

    @SerializedName("FeeShip")
    private long feeShip;

    @SerializedName("IsDOP")
    private int IsDOP;

    @SerializedName("IDHub")
    private String iDHub;
    @SerializedName("ReasonCancelOrder")
    private String ReasonCancelOrder;

    @SerializedName("HubAddress")
    private String hubAddress;

    @SerializedName("FeeCOD")
    private long feeCOD;
    @SerializedName("FeePA")
    private long FeePA;
    @SerializedName("FeeC")
    private long feeC;
    @SerializedName("FeePPA")
    private long feePPA;
    @SerializedName("FeeCollectLater")
    private long feeCollectLater;
    @SerializedName("AmountCOD")
    private long amountCOD;
    @SerializedName("IsCancelOrder ")
    private boolean isCancelOrder;
    @SerializedName("FeeCancelOrder")
    private long FeeCancelOrder;
    @SerializedName("ReceiverVpostcode")
    String ReceiverVpostcode;
    @SerializedName("SenderVpostcode")
    String SenderVpostcode;

    @SerializedName("DeliveryLat")
    private String DeliveryLat;
    @SerializedName("DeliveryLon")
    private String DeliveryLon;
    @SerializedName("ReceiverLat")
    private String ReceiverLat;
    @SerializedName("ReceiverLon")
    private String ReceiverLon;
    @SerializedName("PODeliveryLat")
    private String PODeliveryLat;
    @SerializedName("PODeliveryLon")
    private String PODeliveryLon;
    @SerializedName("ReceiverVerify")
    private int ReceiverVerify;
    @SerializedName("IsWarehouseOutput")
    private boolean IsWarehouseOutput;
    @SerializedName("SenderBookingPhone")
    private String SenderBookingPhone;
    @SerializedName("ReceiverBookingPhone")
    private String ReceiverBookingPhone;

    int mViti = -1;
    int STT = 0;

    public String getSenderBookingPhone() {
        return SenderBookingPhone;
    }

    public void setSenderBookingPhone(String senderBookingPhone) {
        SenderBookingPhone = senderBookingPhone;
    }

    public String getReceiverBookingPhone() {
        return ReceiverBookingPhone;
    }

    public void setReceiverBookingPhone(String receiverBookingPhone) {
        ReceiverBookingPhone = receiverBookingPhone;
    }

    public int getSTT() {
        return STT;
    }

    public void setSTT(int STT) {
        this.STT = STT;
    }

    public boolean isWarehouseOutput() {
        return IsWarehouseOutput;
    }

    public void setWarehouseOutput(boolean warehouseOutput) {
        IsWarehouseOutput = warehouseOutput;
    }

    public int getmViti() {
        return mViti;
    }

    public void setmViti(int mViti) {
        this.mViti = mViti;
    }

    public int getReceiverVerify() {
        return ReceiverVerify;
    }

    public void setReceiverVerify(int receiverVerify) {
        ReceiverVerify = receiverVerify;
    }

    boolean isCheckFeeCancelOrder;

    public boolean isCheckFeeCancelOrder() {
        return isCheckFeeCancelOrder;
    }

    public void setCheckFeeCancelOrder(boolean checkFeeCancelOrder) {
        isCheckFeeCancelOrder = checkFeeCancelOrder;
    }

    public String getReasonCancelOrder() {
        return ReasonCancelOrder;
    }

    public void setReasonCancelOrder(String reasonCancelOrder) {
        ReasonCancelOrder = reasonCancelOrder;
    }

    public String getCuocCH() {
        return CuocCH;
    }

    public void setCuocCH(String cuocCH) {
        CuocCH = cuocCH;
    }

    public Integer getType() {
        return Type;
    }

    public void setType(Integer type) {
        Type = type;
    }

    boolean isCheck;
    boolean isAnItem = false;

    public boolean isAnItem() {
        return isAnItem;
    }

    public void setAnItem(boolean anItem) {
        isAnItem = anItem;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getDeliveryLat() {
        return DeliveryLat;
    }

    public void setDeliveryLat(String deliveryLat) {
        DeliveryLat = deliveryLat;
    }

    public String getDeliveryLon() {
        return DeliveryLon;
    }

    public void setDeliveryLon(String deliveryLon) {
        DeliveryLon = deliveryLon;
    }

    public String getReceiverLat() {
        return ReceiverLat;
    }

    public void setReceiverLat(String receiverLat) {
        ReceiverLat = receiverLat;
    }

    public String getReceiverLon() {
        return ReceiverLon;
    }

    public void setReceiverLon(String receiverLon) {
        ReceiverLon = receiverLon;
    }

    public String getPODeliveryLat() {
        return PODeliveryLat;
    }

    public void setPODeliveryLat(String PODeliveryLat) {
        this.PODeliveryLat = PODeliveryLat;
    }

    public String getPODeliveryLon() {
        return PODeliveryLon;
    }

    public void setPODeliveryLon(String PODeliveryLon) {
        this.PODeliveryLon = PODeliveryLon;
    }

    public String getReferenceCode() {
        return ReferenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        ReferenceCode = referenceCode;
    }

    public String getReceiverVpostcode() {
        return ReceiverVpostcode;
    }

    public void setReceiverVpostcode(String receiverVpostcode) {
        ReceiverVpostcode = receiverVpostcode;
    }

    public String getSenderVpostcode() {
        return SenderVpostcode;
    }

    public void setSenderVpostcode(String senderVpostcode) {
        SenderVpostcode = senderVpostcode;
    }

    public long getFeePA() {
        return FeePA;
    }

    public void setFeePA(long feePA) {
        FeePA = feePA;
    }

    public boolean isCancelOrder() {
        return isCancelOrder;
    }

    public void setCancelOrder(boolean cancelOrder) {
        isCancelOrder = cancelOrder;
    }

    public long getFeeCancelOrder() {
        return FeeCancelOrder;
    }

    public void setFeeCancelOrder(long feeCancelOrder) {
        FeeCancelOrder = feeCancelOrder;
    }

    public long getFeeCollectLater() {
        return feeCollectLater;
    }

    public void setFeeCollectLater(long feeCollectLater) {
        this.feeCollectLater = feeCollectLater;
    }

    public long getAmountCOD() {
        return amountCOD;
    }

    public void setAmountCOD(long amountCOD) {
        this.amountCOD = amountCOD;
    }

    public long getFeeCOD() {
        return feeCOD;
    }

    public void setFeeCOD(long feeCOD) {
        this.feeCOD = feeCOD;
    }

    public long getFeeC() {
        return feeC;
    }

    public void setFeeC(long feeC) {
        this.feeC = feeC;
    }

    public long getFeePPA() {
        return feePPA;
    }

    public void setFeePPA(long feePPA) {
        this.feePPA = feePPA;
    }

    public String getiDHub() {
        return iDHub;
    }

    public void setiDHub(String iDHub) {
        this.iDHub = iDHub;
    }

    public String getHubAddress() {
        return hubAddress;
    }

    public void setHubAddress(String hubAddress) {
        this.hubAddress = hubAddress;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public long getFeeShip() {
        return feeShip;
    }

    public void setFeeShip(long feeShip) {
        this.feeShip = feeShip;
    }

    public int getIsDOP() {
        return IsDOP;
    }

    public void setIsDOP(int isDOP) {
        IsDOP = isDOP;
    }

    public String getsMLStatusName() {
        return sMLStatusName;
    }

    public void setsMLStatusName(String sMLStatusName) {
        this.sMLStatusName = sMLStatusName;
    }

    public DeliveryPostman() {
    }

    public DeliveryPostman(Integer orderNumberInBD13) {
        this.orderNumberInBD13 = orderNumberInBD13;
    }

    public List<ProductModel> getListProducts() {
        return listProducts;
    }

    public void setListProducts(List<ProductModel> listProducts) {
        this.listProducts = listProducts;
    }

    private boolean selected;

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaE() {
        return maE;
    }

    public void setMaE(String maE) {
        this.maE = maE;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderMobile() {
        return senderMobile;
    }

    public void setSenderMobile(String senderMobile) {
        this.senderMobile = senderMobile;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getReciverName() {
        return reciverName;
    }

    public void setReciverName(String reciverName) {
        this.reciverName = reciverName;
    }

    public String getReciverAddress() {
        return reciverAddress;
    }

    public void setReciverAddress(String reciverAddress) {
        this.reciverAddress = reciverAddress;
    }

    public String getReciverMobile() {
        return reciverMobile;
    }

    public void setReciverMobile(String reciverMobile) {
        this.reciverMobile = reciverMobile;
    }

    public String getPoCode() {
        return poCode;
    }

    public void setPoCode(String poCode) {
        this.poCode = poCode;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Integer createDate) {
        this.createDate = createDate;
    }

    public Integer getRoute() {
        return route;
    }

    public void setRoute(Integer route) {
        this.route = route;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getAutoCallStatus() {
        return autoCallStatus;
    }

    public void setAutoCallStatus(String autoCallStatus) {
        this.autoCallStatus = autoCallStatus;
    }

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public Integer getShiftId() {
        return shiftId;
    }

    public void setShiftId(Integer shiftId) {
        this.shiftId = shiftId;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public String getNewInstruction() {
        return newInstruction;
    }

    public void setNewInstruction(String newInstruction) {
        this.newInstruction = newInstruction;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getVatCode() {
        return vatCode;
    }

    public void setVatCode(String vatCode) {
        this.vatCode = vatCode;
    }

    public String getDescription() {
        return description;
    }

    public PointMyVNPost getNewReceiverAddress() {
        return newReceiverAddress;
    }

    public String getbD13CreatedDate() {
        return bD13CreatedDate;
    }

    public String getIsPA() {
        return isPA;
    }

    public String isItemReturn() {
        return isItemReturn;
    }

    public void setItemReturn(String itemReturn) {
        isItemReturn = itemReturn;
    }

    public Integer getOrderNumberInBD13() {
        return orderNumberInBD13;
    }

    public String getAmountForBatch() {
        return amountForBatch;
    }

    public Integer getItemsInBatch() {
        return itemsInBatch;
    }

    public String getReplaceCode() {
        return replaceCode;
    }

    public void setReplaceCode(String replaceCode) {
        this.replaceCode = replaceCode;
    }

    public boolean isPaymentBatch() {
        return isPaymentBatch;
    }

    public String getLastLadingCode() {
        return lastLadingCode;
    }

    public boolean isRePaymentBatch() {
        return isRePaymentBatch;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public Integer getAuthenType() {
        return authenType;
    }

    public String getIsItemReturn() {
        return isItemReturn;
    }

    @Override
    public int compareTo(Object o) {
        int compareOrderNumber = ((DeliveryPostman) o).getOrderNumberInBD13();
        if (orderNumberInBD13 == compareOrderNumber)
            return 0;
        else if (orderNumberInBD13 < compareOrderNumber)
            return -1;
        else
            return 1;
    }
}
