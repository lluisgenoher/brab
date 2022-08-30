package tcm.laq.bitcoinProjectLAQ.domain;

import java.util.UUID;

public class AbsUser {
    protected String id;
    protected String name;
    protected String surname;
    protected String email;
    protected String password;
    protected Account account;

    public AbsUser(String name, String surname, String email, String password) {
        this.id = (UUID.randomUUID()).toString();
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.account = new Account();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
