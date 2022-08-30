package tcm.laq.bitcoinProjectLAQ.domain;

import java.util.*;

public class Auction {
    private String id;
    private String brokerID;
    private Date startDate;
    private Date endDate;
    private double bitcoins;
    private double startBid;
    private TreeSet<Bid> bidding;


    public Auction(Date startDate, Date endDate, double bitcoins, double startBid) {
        this.id = (UUID.randomUUID()).toString();
        this.startDate = startDate;
        this.endDate = endDate;
        this.bitcoins = bitcoins;
        this.startBid = startBid;
        this.bidding = new TreeSet<Bid>();
    }

    public String getId() {
        return id;
    }

    public String getBrokerID() {
        return brokerID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public TreeSet<Bid> getBidding() {
        return bidding;
    }

    public double getBitcoins() {
        return bitcoins;
    }

    public double getStartBid() {
        return startBid;
    }

    public void addBid(Bid newBid) {
        // method to add a new bid
        bidding.add(newBid);
    }



}
