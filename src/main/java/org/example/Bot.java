package org.example;

import org.example.model.Currency;
import org.example.model.User;
import org.example.service.CurrencyService;
import org.example.util.MarkupCreator;
import org.example.util.Stage;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    private final List<User> users = new ArrayList<>();
    private final CurrencyService currencyService = new CurrencyService();

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            long chatId = message.getChatId();

            User user = getUser(chatId);

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(user.getChatId());

            if (message.getText().equals("/start") || message.getText().equals("⤴️" + "Boshidan")) {

                user.setStage(Stage.START);
                sendMessage.setText("Valyuta miqdorini kiriting");
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    System.out.println(e.getMessage());
                }
                user.setStage(Stage.AMOUNT);

            } else if (user.getStage().equals(Stage.AMOUNT)) {

                try {
                    long amount = Long.parseLong(message.getText());
                    user.setAmount(amount);
                } catch (NumberFormatException nfe) {
                    sendMessage.setText("Valyuta miqdorini sonda kiriting");
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        System.out.println(e.getMessage());
                    }
                    return;
                }
                ReplyKeyboardMarkup currencyMarkup = MarkupCreator.createCurrencyMarkup();
                sendMessage.setText("Kiritilgan valyuta turini tanlang");
                sendMessage.setReplyMarkup(currencyMarkup);
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    System.out.println(e.getMessage());
                }
                user.setStage(Stage.FROM_CURRENCY);

            } else if (user.getStage().equals(Stage.FROM_CURRENCY)) {

                try {
                    Currency fromCurrency = Currency.valueOf(message.getText().replaceAll("[^A-Za-z]+", "").toUpperCase());
                    user.setFromCurrency(fromCurrency);
                } catch (IllegalArgumentException iae) {
                    sendMessage.setText("Noto'g'ri valyuta turi tanlandi");
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        System.out.println(e.getMessage());
                    }
                    return;
                }
                ReplyKeyboardMarkup currencyMarkup = MarkupCreator.createCurrencyMarkup(user.getFromCurrency());
                sendMessage.setText("Qaysi valyuta turiga o'girmoqchisiz");
                sendMessage.setReplyMarkup(currencyMarkup);
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    System.out.println(e.getMessage());
                }
                user.setStage(Stage.TO_CURRENCY);

            } else if (user.getStage().equals(Stage.TO_CURRENCY)) {

                try {
                    Currency toCurrency = Currency.valueOf(message.getText().replaceAll("[^A-Za-z]+", "").toUpperCase());
                    user.setToCurrency(toCurrency);
                } catch (IllegalArgumentException iae) {
                    sendMessage.setText("Noto'g'ri valyuta turi tanlandi");
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        System.out.println(e.getMessage());
                    }
                    return;
                }
                double fromCurrencyRate = currencyService.getCurrencyRate(user.getFromCurrency());
                double toCurrencyRate = currencyService.getCurrencyRate(user.getToCurrency());
                if (user.getToCurrency().equals(Currency.UZS)) {
                    sendMessage.setText(String.valueOf(fromCurrencyRate * user.getAmount()));
                } else if (user.getFromCurrency().equals(Currency.UZS)) {
                    sendMessage.setText(String.valueOf(user.getAmount() / toCurrencyRate));
                } else {
                    sendMessage.setText(String.valueOf((fromCurrencyRate / toCurrencyRate) * user.getAmount()));
                }
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    System.out.println(e.getMessage());
                }

            }
        }
    }

    @Override
    public String getBotUsername() {
        return "OrgCurrencyConverterBot";
    }

    @Override
    public String getBotToken() {
        return "6072685654:AAGKvyrayKVJyTZPs9EEDceLjO9IoNo0F6M";
    }

    private User getUser(long chatId) {
        User user = users.stream().filter(u -> u.getChatId() == chatId).findFirst().orElse(null);
        if (user == null) {
            User newUser = new User(chatId, Stage.START, 0, null, null);
            users.add(newUser);
            return newUser;
        }
        return user;
    }
}
