package org.playground.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "lots")
public class Lot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lot_name")
    private String lotName;

    @ManyToOne
    private User seller;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Category category;

    @Column(name = "description")
    private String description;

    @Column(name = "auction_end")
    private LocalDateTime auctionEnd;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "buy_out_price")
    private Double buyOutPrice;

    @OneToMany(mappedBy = "lot", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private Set<Bid> bids;

    public Lot(User seller) {
        this.seller = seller;
    }

    public Lot() {

    }

    public String getLotName() {
        return lotName;
    }

    public void setLotName(String lotName) {
        this.lotName = lotName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public Set<Bid> getBids() {
        return bids;
    }

    public void setBids(Set<Bid> bids) {
        this.bids = bids;
    }
}
