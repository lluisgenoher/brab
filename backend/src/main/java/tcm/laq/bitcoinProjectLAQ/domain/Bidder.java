package tcm.laq.bitcoinProjectLAQ.domain;

import java.util.*;

public class Bidder extends AbsUser {

    private List<Bid> bidding;
    private double blockedMoney;
    private double bitcoins;

    public Bidder(String name, String surname, String email, String password) {
        super(name, surname, email, password);
        this.bidding = new ArrayList<Bid>();
        this.blockedMoney = 0;
        this.bitcoins = 0;
    }

    public List<Bid> getBidding() {
        return bidding;
    }

    public double getBitcoins() {
        return bitcoins;
    }

    public void addMoney(double money) {
        if (money >= 0) {
            super.account.addMoney(money);
        } else {
            // throw Exceptions
        }
    }

    /*public void doBid(double moneyBid, Auction auction) {
        if (moneyBid <= 0) { // throw Exception
            if (super.account.getBalance()-moneyBid <= 0) {
                // throw Exception
            } else {

                bidding.add(new Bid(this, auction, moneyBid));

            }

        }
    }*/

    public double getBlockedMoney() {
        return this.blockedMoney;
    }

    public void setBlockedMoney(double blockedMoney) {
        this.blockedMoney=blockedMoney;
    }

    public void releaseBlockedMoney(double amount) {
        this.blockedMoney=-amount;
        super.account.addMoney(amount);

    }
}
