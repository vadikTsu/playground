package org.playground.dto;


import java.time.LocalDateTime;

public class LotDto {


    private Long id;

    private String lotName;

    private Long sellerId;

    private Long categoryId;

    private String description;

    private LocalDateTime auctionEnd;

    private Boolean active;

    private Double buyOutPrice;

    public LotDto() {
    }

    public LotDto(Long id, String lotName, Long sellerId, Long categoryId, String description, LocalDateTime auctionEnd, Boolean active, Double buyOutPrice) {
        this.id = id;
        this.lotName = lotName;
        this.sellerId = sellerId;
        this.categoryId = categoryId;
        this.description = description;
        this.auctionEnd = auctionEnd;
        this.active = active;
        this.buyOutPrice = buyOutPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLotName() {
        return lotName;
    }

    public void setLotName(String lotName) {
        this.lotName = lotName;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategory(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getAuctionEnd() {
        return auctionEnd;
    }

    public void setAuctionEnd(LocalDateTime auctionEnd) {
        this.auctionEnd = auctionEnd;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Double getBuyOutPrice() {
        return buyOutPrice;
    }

    public void setBuyOutPrice(Double buyOutPrice) {
        this.buyOutPrice = buyOutPrice;
    }
}
