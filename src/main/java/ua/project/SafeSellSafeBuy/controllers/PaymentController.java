package ua.project.SafeSellSafeBuy.controllers;

import com.example.stripe.dto.CreatePayment;
import com.example.stripe.dto.CreatePaymentResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PaymentController {

    @PostMapping("/create-payment-intent")
    public CreatePaymentResponse createPaymentIntent(@RequestBody CreatePayment createPayment) throws StripeException {

//        PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
//                .setCurrency("usd")
//                .setAmount(15*100L) // createPayment... what product does user want to buy... how much does product cost
//                .build();
//        PaymentIntent intent = PaymentIntent.create(createParams);
//        return new CreatePaymentResponse(intent.getClientSecret());

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(15 * 100L)
                .setCurrency("usd")
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods
                                .builder()
                                .setEnabled(true)
                                .build()
                )
                .build();

        // Create a PaymentIntent with the order amount and currency
        PaymentIntent paymentIntent = PaymentIntent.create(params);
        return new CreatePaymentResponse(paymentIntent.getClientSecret());
    }
}


