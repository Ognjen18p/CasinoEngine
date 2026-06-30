package com.controller.page;

import com.application.GameManager;
import com.application.configuration.GameSettings;
import com.application.security.CardValidator;
import com.basis.card.Card;
import com.basis.page.PaymentPage;
import com.basis.transaction.Transaction;
import com.controller.Controller;
import com.application.security.StripeService;
import com.database.card.CardDAO;
import com.database.person.PlayerDAO;
import com.database.transaction.TransactionDAO;
import com.stripe.model.PaymentIntent;
import javafx.scene.Scene;
import javafx.scene.control.TextFormatter;

import java.time.LocalDate;

public class PaymentPageController extends Controller {
    private CardValidator cardValidator;
    private CardDAO cardDAO;
    private TransactionDAO transactionDAO;
    private PlayerDAO playerDAO;
    private PaymentPage paymentPage;
    private final double amount;
    private final Transaction.Type type;

    public PaymentPageController(double amount, Transaction.Type type) {
        this.amount = amount;
        this.type = type;
        initializeScene();
        setupEventHandlers();
    }

    @Override
    protected void initializeScene() {
        cardValidator = new CardValidator();
        cardDAO = new CardDAO();
        transactionDAO = new TransactionDAO();
        playerDAO = new PlayerDAO();
        paymentPage = new PaymentPage();
        scene = new Scene(paymentPage.getMainLayout(),
                GameSettings.getInstance().getWindowWidth(),
                GameSettings.getInstance().getWindowHeight());
    }

    @Override
    public void showScene() {
        GameManager.getInstance().setMainScene(scene);
        GameManager.getInstance().getMainStage().setTitle("Casino Engine - Payment");
    }

    @Override
    protected void setupEventHandlers() {
        handleCardNumberField();
handleCardHolderNameField();
handleCvvNumberField();
         handlePayment();
        paymentPage.getBackButton().setOnAction(event -> GameManager.getInstance().navigateTo(new MenuPageController()));
    }

    private void handleCardNumberField(){
        paymentPage.getCardNumberField().setTextFormatter(new TextFormatter<>(input -> {
            if (input.getControlNewText().matches("[0-9]*")) {
                return input;
            }
            return null;
        }));
    }

    private void handleCardHolderNameField(){
        paymentPage.getCardHolderNameField().setTextFormatter(new TextFormatter<>(input -> {
            if (input.getControlNewText().matches("[a-zA-Z]*")) {
                return input;
            }
            return null;
        }));
    }

    private void handleCvvNumberField   (){
        paymentPage.getCvvField().setTextFormatter(new TextFormatter<>(input -> {
            if (input.getControlNewText().matches("[0-9]*")) {
                return input;
            }
            return null;
        }));
    }

    private void handlePayment() {
        paymentPage.getConfirmButton().setOnAction(event -> {
            String cardNumber = paymentPage.getCardNumberField().getText().trim();
            String cardHolderName = paymentPage.getCardHolderNameField().getText();
            String cvv = paymentPage.getCvvField().getText();
            int expiryMonth = paymentPage.getMonthExpiryField().getValue();
            int expiryYear = paymentPage.getYearExpiryField().getValue();
            int personId = GameManager.getInstance().getCurrentPlayer().getId();

            if (!cardValidator.isNameValid(cardHolderName) || !cardValidator.isNumberValid(cardNumber) ||
                    !cardValidator.isCvvValid(cvv) || !cardValidator.isExpired(expiryMonth, expiryYear)) {
                paymentPage.showErrorMessage(cardValidator.getErrorMessage());
                return;
            }

            try {
                String paymentMethodId = StripeService.createPaymentMethodId("tok_visa");
//            String paymentMethodId = StripeService.createPaymentMethodId("tok_chargeDeclined");
                PaymentIntent paymentIntent = StripeService.createPaymentIntent(amount, paymentMethodId);

                if (paymentIntent == null || !StripeService.confirmPayment(paymentIntent.getId())) {
                    paymentPage.showErrorMessage("Payment declined");
                    saveTransaction(Transaction.Status.DECLINED, personId, 0);
                    return;
                }
                Card card = new Card(personId, cardNumber, cardHolderName, LocalDate.of(expiryYear, expiryMonth, 1));

                if (!cardDAO.cardExists(personId, card))
                    cardDAO.saveCard(personId, card);
                int cardId = cardDAO.getCardId(personId, card);

                saveTransaction(Transaction.Status.APPROVED, personId, cardId);

                if (type == Transaction.Type.DEPOSIT) {
                    if (playerDAO.updateBalance(amount))
                        paymentPage.showErrorMessage("Balance updated successfully");
                    else
                        paymentPage.showErrorMessage("Balance update failed");
                } else {
                    if (GameManager.getInstance().getCurrentPlayer().getBalance() < amount) {
                        paymentPage.showErrorMessage("Not enough balance!");
                        return;
                    }
                    if (playerDAO.updateBalance(-amount))
                        paymentPage.showErrorMessage("Balance updated successfully");
                    else
                        paymentPage.showErrorMessage("Balance update failed!");
                }

                GameManager.getInstance().navigateTo(new CashierPageController());

            } catch (Exception exception) {
                paymentPage.showErrorMessage("Payment error: " + exception.getMessage());
            }
        });
    }

    private void saveTransaction(Transaction.Status status, int personId, int cardId) {
        Transaction transaction = new Transaction(cardId, personId, amount, status, type);
        if(!transactionDAO.createTransaction(transaction))
            paymentPage.showErrorMessage(transactionDAO.getErrorMessage());
    }

}
