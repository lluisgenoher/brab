package tcm.laq.bitcoinProjectLAQ.application.dto;

import tcm.laq.bitcoinProjectLAQ.domain.Auction;

import java.util.Date;
import java.util.TreeSet;
import java.util.UUID;

public class AuctionDTO {
    private String auction_id;
    private String broker_id;
    private Date startDate;
    private Date endDate;
    private TreeSet<BidDTO> bidding;
    private double bitcoins;
    private double startBid;

    public AuctionDTO(Auction auction){
        this.auction_id = this.auction_id =(UUID.randomUUID()).toString();;
        this.broker_id = auction.getBrokerID();
        this.startDate = auction.getStartDate();
        this.endDate = auction.getEndDate();
        this.bitcoins = auction.getBitcoins();
        this.startBid = auction.getStartBid();
        this.bidding = new TreeSet<BidDTO>();
    }
    public AuctionDTO() {
        this.auction_id = (UUID.randomUUID()).toString();
        this.bidding=new TreeSet<BidDTO>();
    }

    public String getAuction_id() {
        return auction_id;
    }

    public void setAuction_id(String auction_id) {
        this.auction_id = auction_id;
    }

    public String getBroker_id() {
        return broker_id;
    }

    public void setBroker_id(String broker_id) {
        this.broker_id = broker_id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public TreeSet<BidDTO> getBidding() {
        return bidding;
    }

    public void setBidding(TreeSet<BidDTO> bidding) {
        this.bidding = bidding;
    }

    public double getBitcoins() {
        return bitcoins;
    }

    public void setBitcoins(double bitcoins) {
        this.bitcoins = bitcoins;
    }

    public double getStartBid() {
        return startBid;
    }

    public void setStartBid(double startBid) {
        this.startBid = startBid;
    }

    @Override
    public String toString() {
        return "AuctionDTO{" +
                "id='" + auction_id + '\'' +
                ", brokerID='" + broker_id + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", bidding=" + bidding +
                ", bitcoins=" + bitcoins +
                ", startBid=" + startBid +
                '}';
    }
}
