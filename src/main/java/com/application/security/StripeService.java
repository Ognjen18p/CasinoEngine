package com.application.security;

import com.application.configuration.GameSettings;
import com.stripe.exception.*;
import com.stripe.model.*;
import com.stripe.net.RequestOptions;
import com.stripe.param.*;

public class StripeService {
    private static final String API_KEY = "sk_test_51Taa0JF6eeLVF3rodSDMYVTXG76M9UFLOPIVY8l7TlS1bVrmnEakhPcTyiIuRMN1JyQzA6MF76rm2FkFjp7GZQQ900ogSKxDOz";

    public static String createPaymentMethodId(String token) {
        try{
            PaymentMethodCreateParams paymentParams = PaymentMethodCreateParams.builder().
                    setType(PaymentMethodCreateParams.Type.CARD).
                    setCard(PaymentMethodCreateParams.Token.builder().setToken(token).build()).build();
            RequestOptions requestOptions = RequestOptions.builder().setApiKey(API_KEY).build();
            System.out.println("Uspelo! id");
            return PaymentMethod.create(paymentParams, requestOptions).getId();
        } catch (StripeException exception) {
            System.out.println(exception.getMessage());
            return exception.getMessage();
        }
    }

    public static PaymentIntent createPaymentIntent(double amount, String paymentMethodId) {
        try{
            PaymentIntentCreateParams paymentParams = PaymentIntentCreateParams.builder().
                    setAmount(GameSettings.getInstance().getCurrency().toCentValue(amount)).
                        setCurrency(GameSettings.getInstance().getCurrency().getStripeCode()).
                    setPaymentMethod(paymentMethodId).
                    setConfirm(true).
                    setAutomaticPaymentMethods(
                            PaymentIntentCreateParams.AutomaticPaymentMethods.builder().
                                    setEnabled(true).
                                    setAllowRedirects(PaymentIntentCreateParams.AutomaticPaymentMethods.AllowRedirects.NEVER).
                                    build()).
                    build();
            RequestOptions requestOptions = RequestOptions.builder().setApiKey(API_KEY).build();
            System.out.println("Uspelo! intent");
            return PaymentIntent.create(paymentParams, requestOptions);
        } catch (StripeException exception) {
            return null;
        }
    }

    public static boolean confirmPayment(String paymentIntentId) {
        try{
            RequestOptions requestOptions = RequestOptions.builder().setApiKey(API_KEY).build();
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId, requestOptions);
            System.out.println("Uspelo!");
            return paymentIntent.getStatus().equals("succeeded");
        } catch (StripeException exception) {
            return false;
        }
    }

}
