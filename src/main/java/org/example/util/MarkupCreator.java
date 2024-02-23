package org.example.util;

import org.example.model.Currency;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class MarkupCreator {
    public static ReplyKeyboardMarkup createCurrencyMarkup() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();
        KeyboardRow keyboardRow3 = new KeyboardRow();

        KeyboardButton uzs = new KeyboardButton(Currency.UZS.getEmoji() + Currency.UZS.name());
        KeyboardButton usd = new KeyboardButton(Currency.USD.getEmoji() + Currency.USD.name());
        keyboardRow1.add(uzs);
        keyboardRow1.add(usd);

        KeyboardButton eur = new KeyboardButton(Currency.EUR.getEmoji() + Currency.EUR.name());
        KeyboardButton cny = new KeyboardButton(Currency.CNY.getEmoji() + Currency.CNY.name());
        keyboardRow2.add(eur);
        keyboardRow2.add(cny);

        KeyboardButton back = new KeyboardButton("⤴️" + "Boshidan");
        keyboardRow3.add(back);

        keyboardRows.add(keyboardRow1);
        keyboardRows.add(keyboardRow2);
        keyboardRows.add(keyboardRow3);

        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(true);
        markup.setKeyboard(keyboardRows);

        return markup;
    }
    public static ReplyKeyboardMarkup createCurrencyMarkup(Currency withoutCurrency) {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();
        KeyboardRow keyboardRow3 = new KeyboardRow();

        KeyboardButton uzs = new KeyboardButton(Currency.UZS.getEmoji() + Currency.UZS.name());
        KeyboardButton usd = new KeyboardButton(Currency.USD.getEmoji() + Currency.USD.name());
        if (!withoutCurrency.equals(Currency.UZS)) {
            keyboardRow1.add(uzs);
        }
        if (!withoutCurrency.equals(Currency.USD)) {
            keyboardRow1.add(usd);
        }

        KeyboardButton eur = new KeyboardButton(Currency.EUR.getEmoji() + Currency.EUR.name());
        KeyboardButton cny = new KeyboardButton(Currency.CNY.getEmoji() + Currency.CNY.name());
        if (!withoutCurrency.equals(Currency.EUR)) {
            keyboardRow2.add(eur);
        }
        if (!withoutCurrency.equals(Currency.CNY)) {
            keyboardRow2.add(cny);
        }

        KeyboardButton back = new KeyboardButton("⤴️" + "Boshidan");
        keyboardRow3.add(back);

        keyboardRows.add(keyboardRow1);
        keyboardRows.add(keyboardRow2);
        keyboardRows.add(keyboardRow3);

        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(true);
        markup.setKeyboard(keyboardRows);

        return markup;
    }
}
