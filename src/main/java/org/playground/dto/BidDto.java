package org.playground.dto;

public class BidDto {

    private Long lotId;

    private Long userId;

    private Double amount;



    public BidDto() {
    }

    public BidDto(Long lotId, Long userId, double amount) {
        this.lotId = lotId;
        this.userId = userId;
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Long getLotId() {
        return lotId;
    }

    public void setLotId(Long lotId) {
        this.lotId = lotId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
