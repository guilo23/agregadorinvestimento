package tech.build.agregadorinvestimento.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name= "tb_billingAdrees")
public class BillingAdrees {

    @Id
    private UUID id;

    @Column(name="street")
    private String street;

    @Column(name="number")
    private Integer number;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name="account_id")
    private Account account;

    public BillingAdrees() {
    }

    public BillingAdrees(UUID id, Account account, String street, Integer number) {
        this.id = id;
        this.account = account;
        this.street = street;
        this.number = number;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
