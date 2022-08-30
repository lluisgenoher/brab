package tcm.laq.bitcoinProjectLAQ.application.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AdminDTO {
    private String id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private List<BidDTO> bidding;
    private double blockedMoney;
    private double bitcoins;

    public AdminDTO() {
        this.id=(UUID.randomUUID()).toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<BidDTO> getBidding() {
        return bidding;
    }

    public void setBidding(List<BidDTO> bidding) {
        this.bidding = bidding;
    }

    public double getBlockedMoney() {
        return blockedMoney;
    }

    public void setBlockedMoney(double blockedMoney) {
        this.blockedMoney = blockedMoney;
    }

    public double getBitcoins() {
        return bitcoins;
    }

    public void setBitcoins(double bitcoins) {
        this.bitcoins = bitcoins;
    }
}
