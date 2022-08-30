package tcm.laq.bitcoinProjectLAQ.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Admin extends AbsUser { //extends absuser

    private double bitcoins;
    private List<Auction> activeAuctions;
    private List<Bid> bidding;
    private double blockedMoney;

    public Admin(String name, String surname, String email, String password) {
        super(name, surname, email,password);
        this.bidding = new ArrayList<Bid>();
        this.blockedMoney = 0;
        this.bitcoins = 0;
        this.activeAuctions = new ArrayList<Auction>();
    }

    public double getMoney() {
        return super.account.getBalance();
    }

    public void setMoney(double amountOfMoney) {
        super.account.setBalance(amountOfMoney);
    }

    public List<Bid> getBidding() {
        return bidding;
    }

    public double getBitcoins() {
        return bitcoins;
    }

    public void addMoney(double money) {
        super.account.addMoney(money);
    }

    /*public void doBid(double moneyBid, Auction auction) {
        if (moneyBid <= 0) { // throw Exception

            if (getMoney() - moneyBid <= 0) {

                // throw Exception

            } else {

                bidding.add(new Bid(this, auction, moneyBid));

            }

        }
    }*/

    public double getBlockedMoney() {
        return blockedMoney;
    }

    public void setBlockedMoney(double blockedMoney) {
        this.blockedMoney = blockedMoney;
    }

    public void releaseBlockedMoney(double amount) {
        this.setBlockedMoney(this.getBlockedMoney()-amount);
        setMoney(getMoney() + amount);

    }

    public void setBitcoins(double bitcoins) {
        this.bitcoins = bitcoins;
    }

    public List<Auction> getActiveAuction() {
        return this.activeAuctions;
    }

    public void addAuction(Date startDate, Date endDate, double bitcoins, double startBid) {

        activeAuctions.add(new Auction(startDate, endDate, bitcoins, startBid));

    }

}
