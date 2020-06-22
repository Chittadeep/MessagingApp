package com.example.messagingapp;

import java.util.ArrayList;

public class Container {
    Long number;
    ArrayList<String> conversation;

    Container(Long number){
        this.number=number;
        conversation = new ArrayList<String>();
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public ArrayList<String> getConversation() {
        return conversation;
    }

    public void setConversation(ArrayList<String> conversation) {
        this.conversation = conversation;
    }

    public void push(String message){
        conversation.add(message);
    }

}
