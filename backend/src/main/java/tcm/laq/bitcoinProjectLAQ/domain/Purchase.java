package tcm.laq.bitcoinProjectLAQ.domain;

import java.time.LocalDate;
import java.util.Date;

public class Purchase {
    private String id;
    private AbsUser user;
    private double amountOfBitcoins;
    private double amountOfMoney;
    private Date datePurchased;

    public Purchase(String id, AbsUser user, double amountOfBitcoins, double amountOfMoney, Date datePurchased) {
        this.id = id;
        this.user = user;
        this.amountOfBitcoins = amountOfBitcoins;
        this.amountOfMoney = amountOfMoney;
        this.datePurchased = datePurchased;
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

    public Date getDatePurchased() {
        return datePurchased;
    }

    public void setDatePurchased(Date datePurchased) {
        this.datePurchased = datePurchased;
    }
}
