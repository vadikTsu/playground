package org.playground.events;

import org.playground.domain.Lot;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class AuctionEndEvent extends ApplicationEvent {

    private Lot lot;

    public AuctionEndEvent(Object source, Lot lot) {
        super(source);
        this.lot = lot;
    }

    public Lot getLot() {
        return lot;
    }
}
