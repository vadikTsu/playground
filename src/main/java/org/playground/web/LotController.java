package org.playground.web;

import org.playground.domain.CartItem;
import org.playground.domain.Category;
import org.playground.domain.Lot;
import org.playground.dto.BidDto;
import org.playground.dto.LotDto;

import org.playground.payload.request.PlaceLotRequest;
import org.playground.repo.CategoryRepository;

import org.playground.repo.UserRepository;
import org.playground.services.LotBiddingService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path= "v1/api", produces = "application/json")
public class LotController {


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private LotBiddingService lotBiddingService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/lots/bid")
    public void placeBid(@RequestBody BidDto bidDto){
        lotBiddingService.placeBid(bidDto);
    }

    @GetMapping("/category")
    public List<Category> getCategories(){
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

    @GetMapping("/lots/{id}")
    public LotDto getCategories(@PathVariable Long id){
       return lotToDto.apply(lotBiddingService.getLotById(id));

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

    @GetMapping("/lots/bid/{id}")
    public BidDto getLastBid(@PathVariable Long id){
        BidDto bidDto = lotBiddingService.getLastBidDtoByLotId(id);
        return bidDto;
    }

    @DeleteMapping("/lots/remove/{id}")
    public void removeLotById(@PathVariable Long id){
       lotBiddingService.removeLotById(id);
    }

    @GetMapping("/lots/search/findByCategoryId")
    public Page<LotDto> getLotDtoList(@RequestParam("id") Long categoryId, Pageable pageable){
        return lotBiddingService.getLotDtoListByCategoryId(categoryId,pageable);
    }

    @GetMapping("/lots/all")
    public List<LotDto> getAllLotDtoList(){
        return lotBiddingService.getAllLotDtoList();
    }

    @GetMapping("lots/user/{sellerId}")
    public List<LotDto> getLotDtoListBySeller(@PathVariable Long sellerId){
        return lotBiddingService.getLotDtoListBySellerId(sellerId);
    }


    @PostMapping("lots/new")
    public ResponseEntity placeNewLot(@RequestBody PlaceLotRequest lotRequest){
        lotBiddingService.placeLot(lotRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("lots/cart/{id}")
    public List<CartItem> getMails(@PathVariable Long id){
        return lotBiddingService.getUsersCartByUserId(id);
    }


}
