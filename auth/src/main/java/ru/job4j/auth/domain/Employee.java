package ru.job4j.auth.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String family;
    private String inn;
    @Temporal(TemporalType.DATE)
    private Date began = new Date();
    @OneToMany
    private List<Person> accounts = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String taxNumber) {
        this.inn = taxNumber;
    }

    public Date getBegan() {
        return began;
    }

    public void setBegan(Date began) {
        this.began = began;
    }

    public List<Person> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Person> accounts) {
        this.accounts = accounts;
    }

    public void addAccount(Person account) {
        this.accounts.add(account);
    }
}
