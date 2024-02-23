package org.example.model;

public enum Currency {
    UZS("\uD83C\uDDFA\uD83C\uDDFF"),
    USD("\uD83C\uDDFA\uD83C\uDDF8"),
    EUR("\uD83C\uDDEA\uD83C\uDDFA"),
    CNY("\uD83C\uDDE8\uD83C\uDDF3");

    private final String emoji;

    Currency(String emoji) {
        this.emoji = emoji;
    }

    public String getEmoji() {
        return emoji;
    }
}
