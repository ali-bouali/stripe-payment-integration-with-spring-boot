package com.bouali.payment.stripe;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PaymentController {

    @PostMapping("/charge")
    public ResponseEntity<PaymentResponse> charge(@RequestBody ChargeRequest request) {
        try {
            Map<String, Object> chargeParams = new HashMap<>();
            chargeParams.put("amount", request.getAmount() * 100);
            chargeParams.put("currency", "usd");
            chargeParams.put("source", request.getToken());
            Charge charge = Charge.create(chargeParams);

            PaymentResponse response = new PaymentResponse();
            response.setId(charge.getId());
            response.setAmount(charge.getAmount().intValue());
            response.setCurrency(charge.getCurrency());
            response.setStatus(charge.getStatus());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}

