package com.ems.dingdong.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeliveryPostman implements Comparable {
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
    private List<DeliveryListRelease> listProducts;

    public DeliveryPostman() {
    }

    public DeliveryPostman(Integer orderNumberInBD13) {
        this.orderNumberInBD13 = orderNumberInBD13;
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

    public List<DeliveryListRelease> getListProducts() {
        return listProducts;
    }

    public void setListProducts(List<DeliveryListRelease> listProducts) {
        this.listProducts = listProducts;
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
