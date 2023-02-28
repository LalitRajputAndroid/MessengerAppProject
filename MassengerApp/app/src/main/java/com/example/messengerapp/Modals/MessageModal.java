package com.example.messengerapp.Modals;

public class MessageModal {
    String message;
    String senderID,messageID;
    String msgtime;

    public MessageModal() {
    }

    public MessageModal(String message, String senderID, String msgtime) {
        this.message = message;
        this.senderID = senderID;
        this.msgtime = msgtime;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getMsgtime() {
        return msgtime;
    }

    public void setMsgtime(String msgtime) {
        this.msgtime = msgtime;
    }

    @Override
    public String toString() {
        return "ChatModal{" +
                "message='" + message + '\'' +
                ", senderID='" + senderID + '\'' +
                ", msgtime='" + msgtime + '\'' +
                '}';
    }
}
