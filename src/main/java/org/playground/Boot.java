package org.playground;

import org.playground.domain.Category;
import org.playground.domain.Lot;
import org.playground.domain.User;
import org.playground.repo.CategoryRepository;
import org.playground.repo.LotRepository;
import org.playground.repo.UserRepository;
import org.playground.services.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class Boot implements CommandLineRunner {

    @Autowired
    private LotRepository lotRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    EmailSenderService emailSenderService;
    @Override
    public void run(String... args) throws Exception {
//        categoryRepository.save(new Category("Coins"));
//        categoryRepository.save(new Category("Guns"));
//        categoryRepository.save(new Category("Other"));
//
//        Lot lot = new Lot();
//        lot.setLotName("Beretta m92");
//        lot.setCategory(categoryRepository.findById(2l).get());
//        lot.setDescription(
//                "The Beretta 92 is a series of semi-automatic pistols designed and manufactured by Beretta of Italy. " +
//                "The model 92 was designed in 1972 and production of many variants in different calibers continues today.");
//        lotRepository.save(lot);

//       List<Lot> lots = lotRepository.findAll();
//       lots.forEach(lot -> lot.setAuctionEnd(LocalDateTime.now()));
//       lotRepository.saveAll(lots);
//        emailSenderService.sendVerificationMessage("vadym_tsudenko@knu.ua");

    }
}
