package model;

import java.util.Date;

public class Message {

    private final String idSender;

    private final String idReceiver;

    private final String message;

    private final Date date;

    public Message(String idSender, String idReceiver, String message, Date date) {
        this.idSender = idSender;
        this.idReceiver = idReceiver;
        this.message = message;
        this.date = date;
    }

    public String getIdSender() {
        return idSender;
    }

    public String getIdReceiver() {
        return idReceiver;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }
}
