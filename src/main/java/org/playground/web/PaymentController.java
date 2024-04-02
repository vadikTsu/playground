package org.playground.web;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.PaymentIntentUpdateParams;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.persistence.EntityNotFoundException;
import org.playground.domain.Lot;
import org.playground.domain.User;
import org.playground.payment.dto.CheckoutPayment;
import org.playground.repo.CartRepository;
import org.playground.repo.LotRepository;
import org.playground.repo.UserRepository;
import org.playground.security.UserDetailsImpl;
import org.playground.services.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api")
public class PaymentController {

    private Gson gson=new Gson();;
    private  @Value("${stripe.key.secret}") String apiKey;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LotRepository lotRepository;


    @PostMapping("/payment")
    public ResponseEntity<String> createPaymentIntent(@RequestBody CheckoutPayment payment) {

        Stripe.apiKey = apiKey;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Long userId = userDetails.getId();

            Map<String, String> metadata = new HashMap<>();
            metadata.put("userId", userId.toString());
            metadata.put("lotId", payment.lotId.toString());

            SessionCreateParams params = SessionCreateParams.builder()
                    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(payment.getSuccessUrl())
                    .setCancelUrl(payment.getCancelUrl())
                    .setPaymentIntentData(SessionCreateParams
                            .PaymentIntentData.builder()
                            .putAllMetadata(metadata).build())
                    .addLineItem(
                            SessionCreateParams.LineItem.builder().setQuantity(1l)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency(payment.getCurrency()).setUnitAmount(payment.getAmount())
                                                    .setProductData(SessionCreateParams.LineItem.PriceData.ProductData
                                                            .builder().setName(payment.getName()).build())
                                                    .build())
                                    .build())
                    .build();
            try {
                Session session = Session.create(params);
                Map<String, String> responseData = new HashMap<>();
                responseData.put("id", session.getId());
                return ResponseEntity.ok(gson.toJson(responseData));
            } catch (Exception e){
                return ResponseEntity.internalServerError().body(e.getMessage());
            }
        }
       return ResponseEntity.status(401).body("Unauthorized user");
    }


    @PostMapping("/webhook")
    public ResponseEntity<String> handleStripeEvent(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) throws SignatureVerificationException {
        String endpointSecret = "whsec_798f716792b5fd5e5db05fd6ab73bd041a81f8224bc93f57b5ef6172be3b88e5";

        try {
            Webhook.constructEvent(payload, sigHeader, endpointSecret);

            JsonObject parsed = JsonParser.parseString(payload).getAsJsonObject();
            JsonObject dataObject = parsed.getAsJsonObject("data").getAsJsonObject("object");
            JsonObject metadata = dataObject.getAsJsonObject("metadata");

            if (metadata != null) {
                Long userId = metadata.get("userId").getAsLong();
                Long lotId = metadata.get("lotId").getAsLong();

                try{
                    User user =  userRepository.findById(userId).orElseThrow(()->new EntityNotFoundException("failed to find a user"));
                    Lot lot =  lotRepository.findById(lotId).orElseThrow(()->new EntityNotFoundException("failed to find a lot"));
                    user.getCart().getCartItems().forEach(item -> {
                        if(item.getLotId()==lotId){
                            item.setStatus("Paid");
                        }
                    });
                    lot.setActive(false);

//                    emailSenderService.sendEmail(user.getEmail());

                    userRepository.save(user);
                    lotRepository.save(lot);

                } catch (Exception e){

                }
                return ResponseEntity.ok("Meta data fetched");
            } else {
                return ResponseEntity.ok("Metadata not found");
            }
        } catch (SignatureVerificationException e) {
            return ResponseEntity.status(400).body("Webhook error: " + e.getMessage());
        }  catch (Exception e) {
            return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
        }
    }
}
