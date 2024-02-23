package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.util.Stage;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private long chatId;
    private Stage stage;
    private long amount;
    private Currency toCurrency;
    private Currency fromCurrency;
}
