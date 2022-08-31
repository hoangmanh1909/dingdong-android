package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.modedata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreateMode {
    @SerializedName("Count")
    @Expose
    private Integer count;
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
    @SerializedName("SenderAddress")
    @Expose
    private String senderAddress;
    @SerializedName("ReciverName")
    @Expose
    private String reciverName;
    @SerializedName("ReciverAddress")
    @Expose
    private String reciverAddress;
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
    @SerializedName("VATCode")
    @Expose
    private String vATCode;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("BatchCode")
    @Expose
    private Object batchCode;
    @SerializedName("NewReceiverAddress")
    @Expose
    private Object newReceiverAddress;
    @SerializedName("ChangeRouteStatus")
    @Expose
    private Object changeRouteStatus;
    @SerializedName("BD13CreatedDate")
    @Expose
    private Object bD13CreatedDate;
    @SerializedName("BD13CreatedDate_VDate")
    @Expose
    private String bD13CreatedDateVDate;
    @SerializedName("IsPA")
    @Expose
    private Object isPA;
    @SerializedName("IsItemReturn")
    @Expose
    private String isItemReturn;
    @SerializedName("OrderNumberInBD13")
    @Expose
    private Integer orderNumberInBD13;
    @SerializedName("AmountForBatch")
    @Expose
    private Object amountForBatch;
    @SerializedName("ItemsInBatch")
    @Expose
    private Integer itemsInBatch;
    @SerializedName("CustomerCode")
    @Expose
    private Object customerCode;
    @SerializedName("AuthenType")
    @Expose
    private Integer authenType;
    @SerializedName("Products")
    @Expose
    private List<Object> products = null;
    @SerializedName("SMLStatusCode")
    @Expose
    private Object sMLStatusCode;
    @SerializedName("SMLStatusName")
    @Expose
    private Object sMLStatusName;
    @SerializedName("IsDOP")
    @Expose
    private Integer isDOP;
    @SerializedName("IDHub")
    @Expose
    private Object iDHub;
    @SerializedName("HubAddress")
    @Expose
    private Object hubAddress;
    @SerializedName("FeeCOD")
    @Expose
    private Integer feeCOD;
    @SerializedName("FeeC")
    @Expose
    private Integer feeC;
    @SerializedName("FeePPA")
    @Expose
    private Integer feePPA;
    @SerializedName("FeeShip")
    @Expose
    private Integer feeShip;
    @SerializedName("FeeCancelOrder")
    @Expose
    private Integer feeCancelOrder;
    @SerializedName("FeeCollectLater")
    @Expose
    private Integer feeCollectLater;
    @SerializedName("FeePA")
    @Expose
    private Integer feePA;
    @SerializedName("ReceiverVpostcode")
    @Expose
    private Object receiverVpostcode;
    @SerializedName("ReferenceCode")
    @Expose
    private String referenceCode;
    @SerializedName("ReceiveCollectFee")
    @Expose
    private Integer receiveCollectFee;
    @SerializedName("IsWarehouseOutput")
    @Expose
    private Boolean isWarehouseOutput;
    @SerializedName("ReceiverLat")
    @Expose
    private String receiverLat;
    @SerializedName("ReceiverLon")
    @Expose
    private String receiverLon;

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

    public String getvATCode() {
        return vATCode;
    }

    public void setvATCode(String vATCode) {
        this.vATCode = vATCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(Object batchCode) {
        this.batchCode = batchCode;
    }

    public Object getNewReceiverAddress() {
        return newReceiverAddress;
    }

    public void setNewReceiverAddress(Object newReceiverAddress) {
        this.newReceiverAddress = newReceiverAddress;
    }

    public Object getChangeRouteStatus() {
        return changeRouteStatus;
    }

    public void setChangeRouteStatus(Object changeRouteStatus) {
        this.changeRouteStatus = changeRouteStatus;
    }

    public Object getbD13CreatedDate() {
        return bD13CreatedDate;
    }

    public void setbD13CreatedDate(Object bD13CreatedDate) {
        this.bD13CreatedDate = bD13CreatedDate;
    }

    public String getbD13CreatedDateVDate() {
        return bD13CreatedDateVDate;
    }

    public void setbD13CreatedDateVDate(String bD13CreatedDateVDate) {
        this.bD13CreatedDateVDate = bD13CreatedDateVDate;
    }

    public Object getIsPA() {
        return isPA;
    }

    public void setIsPA(Object isPA) {
        this.isPA = isPA;
    }

    public String getIsItemReturn() {
        return isItemReturn;
    }

    public void setIsItemReturn(String isItemReturn) {
        this.isItemReturn = isItemReturn;
    }

    public Integer getOrderNumberInBD13() {
        return orderNumberInBD13;
    }

    public void setOrderNumberInBD13(Integer orderNumberInBD13) {
        this.orderNumberInBD13 = orderNumberInBD13;
    }

    public Object getAmountForBatch() {
        return amountForBatch;
    }

    public void setAmountForBatch(Object amountForBatch) {
        this.amountForBatch = amountForBatch;
    }

    public Integer getItemsInBatch() {
        return itemsInBatch;
    }

    public void setItemsInBatch(Integer itemsInBatch) {
        this.itemsInBatch = itemsInBatch;
    }

    public Object getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(Object customerCode) {
        this.customerCode = customerCode;
    }

    public Integer getAuthenType() {
        return authenType;
    }

    public void setAuthenType(Integer authenType) {
        this.authenType = authenType;
    }

    public List<Object> getProducts() {
        return products;
    }

    public void setProducts(List<Object> products) {
        this.products = products;
    }

    public Object getsMLStatusCode() {
        return sMLStatusCode;
    }

    public void setsMLStatusCode(Object sMLStatusCode) {
        this.sMLStatusCode = sMLStatusCode;
    }

    public Object getsMLStatusName() {
        return sMLStatusName;
    }

    public void setsMLStatusName(Object sMLStatusName) {
        this.sMLStatusName = sMLStatusName;
    }

    public Integer getIsDOP() {
        return isDOP;
    }

    public void setIsDOP(Integer isDOP) {
        this.isDOP = isDOP;
    }

    public Object getiDHub() {
        return iDHub;
    }

    public void setiDHub(Object iDHub) {
        this.iDHub = iDHub;
    }

    public Object getHubAddress() {
        return hubAddress;
    }

    public void setHubAddress(Object hubAddress) {
        this.hubAddress = hubAddress;
    }

    public Integer getFeeCOD() {
        return feeCOD;
    }

    public void setFeeCOD(Integer feeCOD) {
        this.feeCOD = feeCOD;
    }

    public Integer getFeeC() {
        return feeC;
    }

    public void setFeeC(Integer feeC) {
        this.feeC = feeC;
    }

    public Integer getFeePPA() {
        return feePPA;
    }

    public void setFeePPA(Integer feePPA) {
        this.feePPA = feePPA;
    }

    public Integer getFeeShip() {
        return feeShip;
    }

    public void setFeeShip(Integer feeShip) {
        this.feeShip = feeShip;
    }

    public Integer getFeeCancelOrder() {
        return feeCancelOrder;
    }

    public void setFeeCancelOrder(Integer feeCancelOrder) {
        this.feeCancelOrder = feeCancelOrder;
    }

    public Integer getFeeCollectLater() {
        return feeCollectLater;
    }

    public void setFeeCollectLater(Integer feeCollectLater) {
        this.feeCollectLater = feeCollectLater;
    }

    public Integer getFeePA() {
        return feePA;
    }

    public void setFeePA(Integer feePA) {
        this.feePA = feePA;
    }

    public Object getReceiverVpostcode() {
        return receiverVpostcode;
    }

    public void setReceiverVpostcode(Object receiverVpostcode) {
        this.receiverVpostcode = receiverVpostcode;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public Integer getReceiveCollectFee() {
        return receiveCollectFee;
    }

    public void setReceiveCollectFee(Integer receiveCollectFee) {
        this.receiveCollectFee = receiveCollectFee;
    }

    public Boolean getWarehouseOutput() {
        return isWarehouseOutput;
    }

    public void setWarehouseOutput(Boolean warehouseOutput) {
        isWarehouseOutput = warehouseOutput;
    }

    public String getReceiverLat() {
        return receiverLat;
    }

    public void setReceiverLat(String receiverLat) {
        this.receiverLat = receiverLat;
    }

    public String getReceiverLon() {
        return receiverLon;
    }

    public void setReceiverLon(String receiverLon) {
        this.receiverLon = receiverLon;
    }
}
