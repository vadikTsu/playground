package org.playground.payment.dto;

public class CheckoutPayment {

    private  String name;
    private  Long userId;
    public Long lotId;
    public String currency;
    public int amount;
    public String cancelUrl;
    public String successUrl;

    public CheckoutPayment() {
    }

    public CheckoutPayment(String name, Long userId, Long lotId, String currency, int amount, String cancelUrl, String successUrl) {
        this.name = name;
        this.userId = userId;
        this.lotId = lotId;
        this.currency = currency;
        this.amount = amount;
        this.cancelUrl = cancelUrl;
        this.successUrl = successUrl;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getLotId() {
        return lotId;
    }

    public void setLotId(Long lotId) {
        this.lotId = lotId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCancelUrl() {
        return cancelUrl;
    }

    public void setCancelUrl(String cancelUrl) {
        this.cancelUrl = cancelUrl;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
