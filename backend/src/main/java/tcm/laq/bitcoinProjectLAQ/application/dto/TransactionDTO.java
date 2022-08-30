package tcm.laq.bitcoinProjectLAQ.application.dto;

import tcm.laq.bitcoinProjectLAQ.domain.Auction;

import java.util.UUID;

public class TransactionDTO {
    private String id;
    private double amountOfMoney;
    private Auction auction;
    private double bitcoins;

    public TransactionDTO(){
        this.id=(UUID.randomUUID()).toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(double amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public double getBitcoins() {
        return bitcoins;
    }

    public void setBitcoins(double bitcoins) {
        this.bitcoins = bitcoins;
    }
}
