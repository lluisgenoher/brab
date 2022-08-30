package tcm.laq.bitcoinProjectLAQ.application.dto;

import tcm.laq.bitcoinProjectLAQ.domain.AbsUser;
import tcm.laq.bitcoinProjectLAQ.domain.Purchase;

import java.util.Date;
import java.util.UUID;

public class PurchaseDTO {
    private String id;
    private AbsUser user;
    private double amountOfBitcoins;
    private double amountOfMoney;
    private Date buydate;

    public PurchaseDTO(Purchase purchase) {
        this.id = purchase.getId();
        this.user = purchase.getUser();
        this.amountOfBitcoins = purchase.getAmountOfBitcoins();
        this.amountOfMoney = purchase.getAmountOfMoney();
        this.buydate = purchase.getDatePurchased();
    }

    public PurchaseDTO() {
        this.id = (UUID.randomUUID()).toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AbsUser getUser() {
        return user;
    }

    public void setUser(AbsUser user) {
        this.user = user;
    }

    public double getAmountOfBitcoins() {
        return amountOfBitcoins;
    }

    public void setAmountOfBitcoins(double amountOfBitcoins) {
        this.amountOfBitcoins = amountOfBitcoins;
    }

    public double getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(double amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    public Date getBuyDate() {
        return buydate;
    }

    public void setBuyDate(Date datePurchased) {
        this.buydate = datePurchased;
    }

}
