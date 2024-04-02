package org.playground.services;

import jakarta.persistence.EntityNotFoundException;
import org.playground.domain.Bid;
import org.playground.domain.CartItem;
import org.playground.domain.Lot;
import org.playground.dto.BidDto;
import org.playground.dto.LotDto;
import org.playground.events.AuctionEventPublisher;
import org.playground.payload.request.PlaceLotRequest;
import org.playground.repo.BidRepository;
import org.playground.repo.CategoryRepository;
import org.playground.repo.LotRepository;
import org.playground.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class LotBiddingService {

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LotRepository lotRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private AuctionEventPublisher auctionEventPublisher;


    public LotBiddingService(BidRepository bidRepository, UserRepository userRepository, LotRepository lotRepository, CategoryRepository categoryRepository) {
        this.bidRepository = bidRepository;
        this.userRepository = userRepository;
        this.lotRepository = lotRepository;
        this.categoryRepository = categoryRepository;
    }

    public Page<LotDto> getLotDtoListByCategoryId(Long categoryId, Pageable pageable){
       return lotRepository.findAllByCategoryId(categoryId, pageable);
    }

    public List<LotDto> getLotDtoListBySellerId(Long sellerId){
       return lotRepository.findAllBySellerId(sellerId).stream().map(lotToDto).collect(Collectors.toList());
    }


    public List<LotDto> getAllLotDtoList(){
       return lotRepository.findAll().stream().map(lotToDto).collect(Collectors.toList());
    }

    private Function<Lot, LotDto> lotToDto = lot -> {
        LotDto lotDto = new LotDto();
        lotDto.setId(lot.getId());
        lotDto.setLotName(lot.getLotName());
        lotDto.setDescription(lot.getDescription());
        lotDto.setBuyOutPrice(lot.getBuyOutPrice());
        lotDto.setActive(lot.getActive());
        lotDto.setAuctionEnd(lot.getAuctionEnd());
        return lotDto;
    };

    public Lot getLotById(Long id) throws RuntimeException{
        return lotRepository.findById(id).orElseThrow(()-> new RuntimeException("Lot not found"));
    }

    public void  removeLotById(Long id){
        this.lotRepository.deleteById(id);
    }

    public void placeBid(BidDto bidDto) {
        try {
            Bid bid = new Bid();
            Lot lot = lotRepository.findById(bidDto.getLotId()).orElseThrow(() -> new RuntimeException("unexisting bid"));
            if (!lot.getActive()){
                throw new RuntimeException("Lot is inactive");
            }
            bid.setLot(lot);
            bid.setBidder(userRepository.findById(bidDto.getUserId()).orElseThrow(() -> new RuntimeException("unexisting user")));
            bid.setAmount(bidDto.getAmount());
            bidRepository.save(bid);
            try {
                messagingTemplate.convertAndSend("/topic/messages/" + bidDto.getLotId().toString(), bidDto);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to proceed payment");
        }
    }

    public BidDto getLastBidDtoByLotId(Long id) {
        Bid bid = bidRepository.findTopByLotIdAndAmount(id).orElseThrow(RuntimeException::new);
        BidDto bidDto = new BidDto(bid.getLot().getId(), bid.getBidder().getId(), bid.getAmount());
        return bidDto;
    }


    public Bid getLastBidByLotId(Long id) {
        return bidRepository.findTopByLotIdAndAmount(id).orElseThrow(RuntimeException::new);
    }


    public void placeLot(PlaceLotRequest request) throws RuntimeException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        Lot lot = new Lot();
        lot.setLotName(request.getName());
        lot.setCategory(categoryRepository.findById(request.getCategoryId()).orElseThrow(RuntimeException::new));
        lot.setSeller(userRepository.findById(request.getSellerId()).orElseThrow(RuntimeException::new));
        lot.setDescription(request.getDescription());
        lot.setAuctionEnd(LocalDateTime.parse(request.getAuctionEnd(), formatter));
        lot.setActive(true);
        lot.setBuyOutPrice(request.getBuyOutPrice());
        lotRepository.save(lot);

    }
    @Transactional
    @Scheduled(fixedDelay = 5000)
    public void handleAuctionEnd(){
        List<Lot> lots = lotRepository.fetchInactive();
        lots.forEach(lot->{
            lot.setActive(false);
            auctionEventPublisher.publishAuctionEvent(lot);
        });
        lotRepository.saveAll(lots);
    }

    @Transactional
    public List<CartItem> getUsersCartByUserId(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Failed to find a user")).getCart().getCartItems();
    }
}
