package tcm.laq.bitcoinProjectLAQ.domain;

import java.util.UUID;

public class Transaction {
    private String id;
    private double amountOfMoney;
    private Auction auction;
    private double bitcoins;

    public Transaction(double money, Auction auction, double bitcoins){
        this.amountOfMoney = money;
        this.auction = auction;
        this.bitcoins = bitcoins;
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
