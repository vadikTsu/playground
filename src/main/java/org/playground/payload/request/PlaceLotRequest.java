package org.playground.payload.request;

public class PlaceLotRequest {
    private String name;
    private Double buyOutPrice;
    private Long categoryId;
    private String description;
    private String auctionEnd;
    private Long sellerId;

    public PlaceLotRequest(String name, Double buyOutPrice, Long categoryId, String description, Long sellerId) {
        this.name = name;
        this.buyOutPrice = buyOutPrice;
        this.categoryId = categoryId;
        this.description = description;
        this.sellerId = sellerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBuyOutPrice() {
        return buyOutPrice;
    }

    public void setBuyOutPrice(Double buyOutPrice) {
        this.buyOutPrice = buyOutPrice;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getAuctionEnd() {
        return auctionEnd;
    }

    public void setAuctionEnd(String auctionEnd) {
        this.auctionEnd = auctionEnd;
    }
}
