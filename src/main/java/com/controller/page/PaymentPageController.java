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
    protected void setupEventHandlers() {
        paymentPage.getConfirmButton().setOnAction(event -> handlePayment());
        paymentPage.getBackButton().setOnAction(event ->
                GameManager.getInstance().returnToPreviousController());
    }

    @Override
    public void showScene() {
        GameManager.getInstance().setMainScene(scene);
        GameManager.getInstance().getMainStage().setTitle("Casino Engine - Payment");
    }

    private void handlePayment() {
        String cardNumber = paymentPage.getCardNumberField().getText().replace(" ", "");
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

            PaymentIntent paymentIntent = StripeService.createPaymentIntent(amount, paymentMethodId);

            if (!StripeService.confirmPayment(paymentIntent.getId())) {
                paymentPage.showErrorMessage("Payment declined");
                saveTransaction(Transaction.Status.DECLINED, personId, -1);
                return;
            }

            Card card = new Card(personId, cardNumber, cardHolderName, LocalDate.of(expiryYear, expiryMonth, 1));

            if (!cardDAO.cardExists(personId, card))
                cardDAO.saveCard(personId, card);
            int cardId = cardDAO.getCardId(personId, card);

            saveTransaction(Transaction.Status.APPROVED, personId, cardId);

            if (type == Transaction.Type.DEPOSIT) {
                double newBalance = GameManager.getInstance().getCurrentPlayer().getBalance() + amount;
                if (playerDAO.updateBalance(newBalance))
                    GameManager.getInstance().getCurrentPlayer().setBalance(newBalance);
                else
                    paymentPage.showErrorMessage("Balance update failed!");
            } else {
                double currentBalance = GameManager.getInstance().getCurrentPlayer().getBalance();
                if (currentBalance < amount) {
                    paymentPage.showErrorMessage("Not enough balance!");
                    return;
                }
                double newBalance = currentBalance - amount;
                if (playerDAO.updateBalance(newBalance))
                    GameManager.getInstance().getCurrentPlayer().setBalance(newBalance);
                else
                    paymentPage.showErrorMessage("Balance update failed!");
            }

            GameManager.getInstance().returnToPreviousController();

        } catch (Exception exception) {
            paymentPage.showErrorMessage("Payment error: " + exception.getMessage());
        }
    }

    private void saveTransaction(Transaction.Status status, int personId, int cardId) {
        Transaction transaction = new Transaction(cardId, personId, amount, status, type);
        transactionDAO.createTransaction(transaction);
    }

}
