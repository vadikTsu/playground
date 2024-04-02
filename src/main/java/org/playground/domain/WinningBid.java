package org.playground.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "winning_bid")
public class WinningBid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Bid bid;


    private Boolean isBuyOut;

    public WinningBid() {
    }

    public WinningBid(Bid bid, Boolean isBuyOut) {
        this.bid = bid;
        this.isBuyOut = isBuyOut;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Bid getBid() {
        return bid;
    }

    public void setBid(Bid bid) {
        this.bid = bid;
    }

    public Boolean getBuyOut() {
        return isBuyOut;
    }

    public void setBuyOut(Boolean buyOut) {
        isBuyOut = buyOut;
    }
}
