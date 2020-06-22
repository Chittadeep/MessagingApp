package com.example.messagingapp;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Member implements Serializable {
    String name;
    long phoneNum;
    String mail_id;
    String address;
    String designation;
    String password;
    HashMap<String, ArrayList<Message>> messageBox;

    public Member(String name, long phoneNum, String mail_id, String address, String designation, String password) throws Exception{
        if(name.trim().isEmpty())
            throw new Exception("please enter your name correctly");
        this.name = name;

        this.phoneNum = phoneNum;

        if(mail_id.trim().isEmpty() && (!mail_id.endsWith("@gmail.com") || (!mail_id.endsWith(".in")))&& !mail_id.contains("@"))
            throw new Exception("please enter a valid Email-Id");
        this.mail_id = mail_id;

        if(address.trim().isEmpty())
            throw new Exception("please enter your address");
        this.address = address;

        if(designation.trim().isEmpty())
            throw new Exception("no designation??");
        this.designation = designation;

        if(password.trim().isEmpty()||password.length()<6)
            throw new Exception("password should be at least of 6 characters");
        this.password = password;

        messageBox = new HashMap<>();

    }

    public Member()
    {
        messageBox = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, ArrayList<Message>> getMessageBox() {
        return messageBox;
    }

    public void setMessageBox(HashMap<String, ArrayList<Message>> messageBox) {
        this.messageBox = messageBox;
    }

    public long getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(long phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getMail_id() {
        return mail_id;
    }

    public void setMail_id(String mail_id) {
        this.mail_id = mail_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void push(long phoneNum, Message message) {

        String num = Long.toString(phoneNum);
        ArrayList<Message> conversation = messageBox.containsKey(num)?messageBox.get(num): new ArrayList<Message>();
        conversation.add(message);
        HashMap<String, ArrayList<Message>> temp = (HashMap<String, ArrayList<Message>>) messageBox.clone();
        messageBox.clear();
        messageBox.put(num,conversation);
        messageBox.putAll(temp);
    }

    public static  long checkNum(String num) throws Exception
    {
        if (num.trim().isEmpty())
            throw new Exception("enter the number field");
        try {
            long number = Long.parseLong(num);
            if(num.length()!=10)
                throw new Exception("phone numbers are of 10 digits");
            return number;
        }
        catch (NumberFormatException e)
        {
            throw new Exception("number does not contains any type of characters");
        }

    }
}
