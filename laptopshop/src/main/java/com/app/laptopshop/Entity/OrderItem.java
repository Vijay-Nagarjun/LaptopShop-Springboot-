package com.app.laptopshop.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;


@Entity
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "orderitemid"
)
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderitemid;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "orderid")
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "laptopid")
    private Laptop laptop;

    public OrderItem() {
    }

    public OrderItem(Long orderitemid, Integer quantity, Orders order, Laptop laptop) {
        this.orderitemid = orderitemid;
        this.quantity = quantity;
        this.order = order;
        this.laptop = laptop;
    }

    public Long getOrderitemid() {
        return orderitemid;
    }

    public void setOrderitemid(Long orderitemid) {
        this.orderitemid = orderitemid;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public Laptop getLaptop() {
        return laptop;
    }

    public void setLaptop(Laptop laptop) {
        this.laptop = laptop;
    }
}