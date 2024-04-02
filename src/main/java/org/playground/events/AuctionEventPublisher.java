package org.playground.events;

import org.playground.domain.Lot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class AuctionEventPublisher {

    @Autowired
    private ApplicationEventPublisher publisher;

    public void publishAuctionEvent(Lot lot){
        publisher.publishEvent(new AuctionEndEvent(this, lot));
    }
}
