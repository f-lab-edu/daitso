package com.flab.daitso.dto.order;

public class OrderRequestDto {

    public enum Coupon {
        COUPON1(0.1), COUPON2(0.2), COUPON3(0.3);

        private final double amount;

        Coupon(double amount){
            this.amount = amount;
        }
        public double getAmount(){
            return this.amount;
        }
    }

    public enum PaymentOption {
        CREDITCARD, PHONEBILL, ACCOUNTTRANSFER, TRANSPER
    }

    //buyer information
    private String buyerName;
    private String buyerEmail;
    private String buyerPhoneNumber;
    //receiver information
    private String receiverName;
    private String receiverAddress;
    private String receiverPhoneNumber;
    private String deliveryMessage;
    //payment information

    //coupon options(enum??)
    private Coupon coupon;
    private PaymentOption paymentOption;

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public String getBuyerPhoneNumber() {
        return buyerPhoneNumber;
    }

    public void setBuyerPhoneNumber(String buyerPhoneNumber) {
        this.buyerPhoneNumber = buyerPhoneNumber;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverPhoneNumber() {
        return receiverPhoneNumber;
    }

    public void setReceiverPhoneNumber(String receiverPhoneNumber) {
        this.receiverPhoneNumber = receiverPhoneNumber;
    }

    public String getDeliveryMessage() {
        return deliveryMessage;
    }

    public void setDeliveryMessage(String deliveryMessage) {
        this.deliveryMessage = deliveryMessage;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public PaymentOption getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(PaymentOption paymentOption) {
        this.paymentOption = paymentOption;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "buyerName='" + buyerName + '\'' +
                ", buyerEmail='" + buyerEmail + '\'' +
                ", buyerPhoneNumber='" + buyerPhoneNumber + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", receiverAddress='" + receiverAddress + '\'' +
                ", receiverPhoneNumber='" + receiverPhoneNumber + '\'' +
                ", deliveryMessage='" + deliveryMessage + '\'' +
                ", coupon=" + coupon +
                ", paymentOption=" + paymentOption +
                '}';
    }
}
