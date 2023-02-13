package com.example.arttreknew;

public class MessageModel {
    private  String msgId;
    private  String senderEmail;
    private  String message;

    public MessageModel(String msgId, String senderEmail, String message) {
        this.msgId = msgId;
        this.senderEmail = senderEmail;
        this.message = message;
    }


    public MessageModel() {
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
