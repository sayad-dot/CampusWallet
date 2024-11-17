package org.example.campuswallet;



import java.util.Date;

public class Transaction {
    private int id;
    private String senderUsername;
    private String receiverUsername;
    private double amount;
    private Date timestamp;

    // Constructor
    public Transaction(int id, String senderUsername, String receiverUsername, double amount, Date timestamp) {
        this.id = id;
        this.senderUsername = senderUsername;
        this.receiverUsername = receiverUsername;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
