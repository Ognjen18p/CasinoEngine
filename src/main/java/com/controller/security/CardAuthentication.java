package com.controller.security;

import com.application.configuration.GameSettings;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

public class CardAuthentication {

    private static final String API_KEY = "sk_test_...";

    static {
        Stripe.apiKey = API_KEY;
    }

    public static PaymentIntent createPaymentIntent(double amount) {
        try{
            PaymentIntentCreateParams paymentParams = PaymentIntentCreateParams.builder().
                    setAmount(GameSettings.getInstance().getCurrency().toCentValue(amount)).
                        setCurrency(GameSettings.getInstance().getCurrency().getStripeCode()).build();
            return PaymentIntent.create(paymentParams);
        } catch (StripeException exception) {
            throw new RuntimeException("Unable to create PaymentIntent: " + exception.getMessage());
        }
    }

    public static boolean confirmPayment(String paymentIntentId) {
        try{
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
            return paymentIntent.getStatus().equals("succeeded");
        } catch (StripeException exception) {
            throw new RuntimeException("Payment acceptance failed: " + exception.getMessage());
        }
    }

}
