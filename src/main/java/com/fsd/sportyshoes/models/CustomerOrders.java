package com.fsd.sportyshoes.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class CustomerOrders {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Products products;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;

    private Date orderDate;


    public CustomerOrders() {
    }

    public CustomerOrders(long id, Products products, Users user) {
        this.id = id;
        this.products = products;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
