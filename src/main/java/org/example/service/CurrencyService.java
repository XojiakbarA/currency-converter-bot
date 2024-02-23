package org.example.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.model.CBCurrency;
import org.example.model.Currency;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;

public class CurrencyService {
    public double getCurrencyRate(Currency currency) {
        double rate = 0;
        try {
            URL url = new URL("https://cbu.uz/uz/arkhiv-kursov-valyut/json/");
            URLConnection connection = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            Gson gson = new Gson();
            Set<CBCurrency> set = gson.fromJson(reader, new TypeToken<HashSet<CBCurrency>>(){}.getType());
            CBCurrency foundCurrency = set.stream().filter(cbCurrency -> cbCurrency.getCcy().equals(currency.name())).findFirst().orElse(null);
            if (foundCurrency != null) {
                rate = foundCurrency.getRate();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return rate;
    }
}
