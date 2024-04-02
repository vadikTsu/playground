package org.playground.events;

import jakarta.persistence.EntityNotFoundException;
import org.playground.domain.*;
import org.playground.repo.UserRepository;
import org.playground.repo.WinningBidRepository;
import org.playground.services.EmailSenderService;
import org.playground.services.LotBiddingService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WinningBidEventListener {

    final WinningBidRepository winningBidRepository;
    final UserRepository userRepository;

    final EmailSenderService emailSenderService;

    private final LotBiddingService lotBiddingService;

    public WinningBidEventListener(LotBiddingService lotBiddingService, WinningBidRepository winningBidRepository, UserRepository userRepository, EmailSenderService emailSenderService) {
        this.lotBiddingService = lotBiddingService;
        this.winningBidRepository = winningBidRepository;
        this.userRepository = userRepository;
        this.emailSenderService = emailSenderService;
    }

    @EventListener
    public void onAuctionEndEvent(AuctionEndEvent auctionEndEvent){

        try {
            Lot lot = auctionEndEvent.getLot();
            Bid bid = lotBiddingService.getLastBidByLotId(lot.getId());
            winningBidRepository.save(new WinningBid(bid, false));

            User seller = userRepository.findById(lot.getSeller().getId()).orElseThrow(() -> new EntityNotFoundException("Cannot find the user with id: " + lot.getSeller().getId()));
            User bidder = userRepository.findById(bid.getBidder().getId()).orElseThrow(() -> new EntityNotFoundException("Cannot find the user with id: " + bid.getBidder().getId()));

            ensureCart(bidder);

            CartItem cartItem = new CartItem(bidder.getCart());
            cartItem.setName(lot.getLotName());
            cartItem.setStatus("In Cart");
            cartItem.setLotId(lot.getId());
            cartItem.setLotId(lot.getId());
            cartItem.setTime(LocalDateTime.now());
            cartItem.setMessage("Your recent Bid has won!");

            bidder.getCart().addToCart(cartItem);

            userRepository.save(bidder);

            emailSenderService.sendEmail(bidder.getEmail(), "","");

        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    private void ensureCart(User user) {
        if (user.getCart() == null) {
            user.setCart(new Cart(user));
        }
    }

}
