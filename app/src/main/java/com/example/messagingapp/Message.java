package com.example.messagingapp;

import java.io.Serializable;

public class Message implements Serializable {
    //long num;
    String message;
    boolean read;
    boolean isFromUser=false;

    public Message(/*long num,*/ String message, boolean isFromUser) throws Exception {
        //this.num = num;
        if(message.trim().length()<1)
            throw new Exception("please enter message");
        this.message = message;
        read = false;
        this.isFromUser = isFromUser;
    }

    public Message()
    {
        isFromUser=false;
                read = true;
    }

    public boolean isRead() {
        return read;
    }

    public boolean isFromUser() {
        return isFromUser;
    }

    public void setFromUser(boolean fromUser) {
        isFromUser = fromUser;
    }


    public void setRead(boolean read) {
        this.read = read;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    /*public String toString()
    {
        //return getNum()+": "+getMessage();
        return getMessage();
    }*/

    }

