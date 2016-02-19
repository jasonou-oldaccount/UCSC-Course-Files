package com.example.messiah.heyneighbor;

public class ChatMessage {
    public boolean left;
    public String message;
    public String name;
    public String date;

    public ChatMessage(boolean left, String message, String name, String date) {
        super();
        this.left = left;
        this.message = message;
        this.name = name;
        this.date = date;
    }
}
