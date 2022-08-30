package tcm.laq.bitcoinProjectLAQ.domain;

import java.util.UUID;

public class Bid {

    private String id;
    private String idBidder;
    private String idAuction;
    private double moneyBid;
    private double bitcoinsBid;
    private boolean bidAccepted;

    public Bid(String idBidder, String idAuction, double moneyBid) {
        this.id = (UUID.randomUUID()).toString();
        this.idBidder = idBidder;
        this.idAuction = idAuction;
        this.moneyBid = moneyBid;
        this.bidAccepted = false;
    }

    public String getId() {
        return id;
    }

    public String getIdBidder() {
        return idBidder;
    }


    public String getIdAuction() {
        return idAuction;
    }

    public double getMoneyBid() {
        return moneyBid;
    }

    public double getBitcoinsBid() {
        return bitcoinsBid;
    }

    public void setBitcoinsBid(double bitcoinsBid) {
        this.bitcoinsBid = bitcoinsBid;
    }

    public boolean isBidAccepted() {
        return bidAccepted;
    }

    public double compareTo(Bid anotherBid) {
        return  anotherBid.moneyBid - this.moneyBid;
    }

    public boolean equals(Bid anotherBid) {
        return this.compareTo(anotherBid)==0;
    }

}
