package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class CommonObject extends RealmObject {

    //region xacnhantin
    @SerializedName("OrderPostmanID")
    String orderPostmanID;

    @SerializedName("Count")
    String count;
    @SerializedName("Description")
    String description;
    @SerializedName("AssignDateTime")
    String assignDateTime;
    @SerializedName("AssignFullName")
    String assignFullName;
    @SerializedName("Quantity")
    String quantity;
    //endregion
    //region hoantattin
    @SerializedName("TrackingCode")
    String trackingCode;

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }

    @SerializedName("OrderNumber")
    String orderNumber;
    //endregion
    //region common
    @SerializedName("StatusCode")
    String statusCode;
    @SerializedName("AutoCallStatus")
    String autoCallStatus;

    @PrimaryKey
    @SerializedName(value = "Code", alternate = {"MaE", "ParcelCode"})
    String code;

    @SerializedName(value = "ID", alternate = {"Id"})
    String iD;
    @SerializedName(value = "ContactName", alternate = {"ReciverName", "ReceiverName"})
    String receiverName;
    @SerializedName(value = "ContactPhone", alternate = {"ReciverMobile", "ReceiverPhone"})
    String receiverPhone;
    @SerializedName(value = "ContactAddress", alternate = {"ReciverAddress", "ReceiverAddress"})
    String receiverAddress;
    @SerializedName(value = "Weigh", alternate = {"Weight"})
    String weigh;
    //endregion
    //region baophatbangke
    @SerializedName("SenderName")
    String senderName;
    @SerializedName(value = "SenderPhone", alternate = {"SenderMobile"})
    String senderPhone;
    @SerializedName("SenderAddress")
    String senderAddress;
    @SerializedName("PoCode")
    String poCode;
    @SerializedName(value = "CreateDate")
    String createDate;
    @SerializedName("LoadDate")
    String loadDate;
    @SerializedName("Route")
    String route;
    @SerializedName("Order")
    String order;
    @SerializedName("SignatureCapture")
    String signatureCapture;
    @SerializedName("Amount")
    String Amount;
    @SerializedName("Service")
    String Service;
    @SerializedName("Info")
    String Info;
    @SerializedName("Instruction")
    String instruction;
    @SerializedName("RouteCode")
    String routeCode;
    @SerializedName("BatchCode")
    String batchCode;
    @SerializedName("Fee")
    Integer fee;
    @SerializedName("Value")
    Integer cod;
    @SerializedName("OrderId")
    private long orderId;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    @SerializedName("RealReceiverName")
    private String realReceiverName;

    private String realReceiverIDNumber;
    private String DeliveryType;
    private String userDelivery;
    private String paymentChanel;
    private String imageDelivery;
    private String imageAuthen;///
    private boolean selected;
    private boolean local;
    //endregion
    @SerializedName("IsCOD")
    String isCOD;
    @SerializedName("DeliveryDate")
    private String deliveryDate;
    @SerializedName("DeliveryTime")
    private String deliveryTime;
    @SerializedName("CollectAmount")
    private String collectAmount;
    @SerializedName("ReceiveCollectFee")
    private String receiveCollectFee;
    @SerializedName("ReceiverIDNumber")
    private String receiverIDNumber;
    @SerializedName("DeliveryPOCode")
    private String deliveryPOCode;

    @SerializedName("StatusName")
    private String statusName;
    @SerializedName("ReasonName")
    private String reasonName;
    @SerializedName("SolutionName")
    private String solutionName;

    @SerializedName("ReasonCode")
    private String reasonCode;
    @SerializedName("SolutionCode")
    private String solutionCode;

    @SerializedName("CheckStatus")
    private boolean checkStatus;
    @SerializedName("CheckStatusNo")
    private boolean checkStatusNo;
    @SerializedName("Status")
    private String status;

    @SerializedName("ListStatus")
    private RealmList<StatusInfo> statusInfoArrayList;

    @SerializedName("Note")
    private String note;

    @SerializedName("ServiceName")
    private String serviceName;
    @SerializedName("IsPaypost")
    private String isPaypost;
    @SerializedName("ShiftId")
    private String shiftId;

    private String dateSearch;
    @SerializedName("Shipments")
    private RealmList<ParcelCodeInfo> listParcelCode;
    @Ignore
    List<String> orderPostmanIDS;
    @Ignore
    List<String> codeS;
    @Ignore
    public int weightS = 0;

    public void addOrderPostmanID(String id) {
        if (orderPostmanIDS == null) {
            orderPostmanIDS = new ArrayList<>();
        }
        orderPostmanIDS.add(id);
    }

    public void addCode(String code) {
        if (codeS == null) {
            codeS = new ArrayList<>();
        }
        codeS.add(code);
    }

    public List<String> getOrderPostmanIDS() {
        return orderPostmanIDS;
    }

    public List<String> getCodeS() {
        return codeS;
    }

    public String getAutoCallStatus() {
        return autoCallStatus;
    }

    public List<ParcelCodeInfo> getListParcelCode() {
        return listParcelCode;
    }

    public String getSignatureCapture() {
        return signatureCapture;
    }

    public String getCount() {
        return count;
    }

    public String getCode() {
        return code;
    }

    public String getParcelCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getReciverName() {
        return receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }


    public String getReciverAddress() {
        return receiverAddress;
    }

    public String getOrderPostmanID() {
        return orderPostmanID;
    }

    public String getAssignDateTime() {
        return assignDateTime;
    }

    public String getAssignFullName() {
        return assignFullName;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getWeigh() {
        return weigh;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getiD() {
        return iD;
    }

    public String getRoute() {
        return route;
    }

    public String getOrder() {
        return order;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public String getPoCode() {
        return poCode;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public String getIssuePOCode() {
        return poCode;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setRealReceiverName(String realReceiverName) {
        this.realReceiverName = realReceiverName;
    }

    public void setRealReceiverIDNumber(String realReceiverIDNumber) {
        this.realReceiverIDNumber = realReceiverIDNumber;
    }

    public String getDeliveryType() {
        return DeliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        DeliveryType = deliveryType;
    }

    public void setUserDelivery(String userDelivery) {
        this.userDelivery = userDelivery;
    }

    public void setPaymentChanel(String paymentChanel) {
        this.paymentChanel = paymentChanel;
    }

    public void setCollectAmount(String collectAmount) {
        this.collectAmount = collectAmount;
    }

    public String getRealReceiverName() {
        return realReceiverName;
    }

    public String getRealReceiverIDNumber() {
        return realReceiverIDNumber;
    }

    public String getUserDelivery() {
        return userDelivery;
    }

    public String getPaymentChannel() {
        return paymentChanel;
    }

    public String getIsCOD() {
        return isCOD;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public String getCollectAmount() {
        return collectAmount;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public String getReceiveCollectFee() {
        return receiveCollectFee;
    }

    public String getReceiverIDNumber() {
        return receiverIDNumber;
    }


    public String getReceiverAddress() {
        return receiverAddress;
    }

    public String getDeliveryPOCode() {
        return deliveryPOCode;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getStatusName() {
        return statusName;
    }


    public String getReasonName() {
        return reasonName;
    }

    public String getSolutionName() {
        return solutionName;
    }

    public boolean isCheckStatus() {
        return checkStatus;
    }

    public boolean isCheckStatusNo() {
        return checkStatusNo;
    }

    public ArrayList<StatusInfo> getStatusInfoArrayList() {
        ArrayList<StatusInfo> statusInfos = new ArrayList<>();
        if (statusInfoArrayList != null) {
            statusInfos.addAll(statusInfoArrayList);
        }
        return statusInfos;
    }

    public String getLoadDate() {
        return loadDate;
    }

    public String getNote() {
        return note;
    }

    public String getStatus() {
        return status;
    }

    public String getAmount() {
        return Amount;
    }

    public String getService() {
        return Service;
    }

    public String getInfo() {
        return Info;
    }

    public String getDateSearch() {
        return dateSearch;
    }

    public void setDateSearch(String dateSearch) {
        this.dateSearch = dateSearch;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public void setSignatureCapture(String signatureCapture) {
        this.signatureCapture = signatureCapture;
    }

    public void setSaveLocal(boolean islocal) {
        local = islocal;
    }

    public String getIsPaypost() {
        return isPaypost;
    }


    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getSolutionCode() {
        return solutionCode;
    }

    public void setSolutionCode(String solutionCode) {
        this.solutionCode = solutionCode;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }

    public void setSolutionName(String solutionName) {
        this.solutionName = solutionName;
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public String getImageDelivery() {
        return imageDelivery;
    }

    public void setImageDelivery(String imageDelivery) {
        this.imageDelivery = imageDelivery;
    }
    ///
    public String getImageAuthen() {
        return imageAuthen;
    }
    public void setImageAuthen(String imageAuthen) {
        this.imageAuthen = imageAuthen;
    }

    public String getShiftId() {
        return shiftId;
    }

    public Integer getFee() {
        return fee;
    }

    public Integer getCod() {
        return cod;
    }

    public void setOrderPostmanID(String orderPostmanID) {
        this.orderPostmanID = orderPostmanID;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAssignDateTime(String assignDateTime) {
        this.assignDateTime = assignDateTime;
    }

    public void setAssignFullName(String assignFullName) {
        this.assignFullName = assignFullName;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setAutoCallStatus(String autoCallStatus) {
        this.autoCallStatus = autoCallStatus;
    }

    public void setiD(String iD) {
        this.iD = iD;
    }

    public void setWeigh(String weigh) {
        this.weigh = weigh;
    }

    public void setPoCode(String poCode) {
        this.poCode = poCode;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public void setLoadDate(String loadDate) {
        this.loadDate = loadDate;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setService(String service) {
        Service = service;
    }

    public void setInfo(String info) {
        Info = info;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    public String getPaymentChanel() {
        return paymentChanel;
    }

    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    public void setIsCOD(String isCOD) {
        this.isCOD = isCOD;
    }

    public void setReceiveCollectFee(String receiveCollectFee) {
        this.receiveCollectFee = receiveCollectFee;
    }

    public void setReceiverIDNumber(String receiverIDNumber) {
        this.receiverIDNumber = receiverIDNumber;
    }

    public void setDeliveryPOCode(String deliveryPOCode) {
        this.deliveryPOCode = deliveryPOCode;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public void setCheckStatus(boolean checkStatus) {
        this.checkStatus = checkStatus;
    }

    public void setCheckStatusNo(boolean checkStatusNo) {
        this.checkStatusNo = checkStatusNo;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStatusInfoArrayList(RealmList<StatusInfo> statusInfoArrayList) {
        this.statusInfoArrayList = statusInfoArrayList;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setIsPaypost(String isPaypost) {
        this.isPaypost = isPaypost;
    }

    public void setShiftId(String shiftId) {
        this.shiftId = shiftId;
    }

    public void setListParcelCode(RealmList<ParcelCodeInfo> listParcelCode) {
        this.listParcelCode = listParcelCode;
    }
}
