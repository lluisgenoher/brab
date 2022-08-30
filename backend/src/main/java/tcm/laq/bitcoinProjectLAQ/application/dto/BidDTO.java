package tcm.laq.bitcoinProjectLAQ.application.dto;

import java.util.UUID;

public class BidDTO {

    private String BID_ID;
    private String admin_id;
    private String bidder_id;
    private String auction_id;
    private double moneyBid;
    private double bitcoins;
    private boolean bidAccepted;

    public BidDTO(){
        this.BID_ID =(UUID.randomUUID()).toString();
    }

    public BidDTO(String idBidder, double moneyBid) {
        this.BID_ID = (UUID.randomUUID()).toString();
        this.bidder_id = idBidder;
        this.auction_id = auction_id;
        this.moneyBid = moneyBid;
        this.bidAccepted = false;
    }


    public String getBidder_id() {
        return bidder_id;
    }

    public void setBidder_id(String bidder_id) {
        this.bidder_id = bidder_id;
    }

    public String getBID_ID() {
        return BID_ID;
    }

    public void setBID_ID(String BID_ID) {
        this.BID_ID = BID_ID;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getAuction_id() {
        return auction_id;
    }

    public void setAuction_id(String auction_id) {
        this.auction_id = auction_id;
    }

    public double getMoneyBid() {
        return moneyBid;
    }

    public void setMoneyBid(double moneyBid) {
        this.moneyBid = moneyBid;
    }

    public double getBitcoins() {
        return bitcoins;
    }

    public void setBitcoins(double bitcoins) {
        this.bitcoins = bitcoins;
    }

    public boolean isBidAccepted() {
        return bidAccepted;
    }

    public void setBidAccepted(boolean bidAccepted) {
        this.bidAccepted = bidAccepted;
    }
}
