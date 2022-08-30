package tcm.laq.bitcoinProjectLAQ.domain;

import java.util.*;

public class Broker extends AbsUser {

    private double bitcoins;
    private List<Auction> activeAuctions;
    private List<Purchase> purchases;

    public Broker(String name, String surname, String email, String password) {
        super(name, surname, email,password);
        this.activeAuctions = new ArrayList<Auction>();
        this.bitcoins = 0;
        this.purchases = new ArrayList<Purchase>();
    }

    public double getBitcoins() {
        return bitcoins;
    }

    public void setBitcoins(double bitcoins) {
        this.bitcoins = bitcoins;
    }

    public List<Auction> getActiveAuction() {
        return this.activeAuctions;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void addAuction(Date startDate, Date endDate, double bitcoins, double startBid) {

        activeAuctions.add(new Auction(startDate, endDate, bitcoins, startBid));

    }

   /* public void addPurchase(double bitcoins, double money) {

        purchases.add(new Purchase(bitcoins, money));

    }*/


}
