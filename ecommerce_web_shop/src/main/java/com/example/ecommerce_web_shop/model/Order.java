package com.example.ecommerce_web_shop.model;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String address;
    private String city;
    @Column()
    private double totalPrice;

    @CreationTimestamp
    private LocalDate dateCreated;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderContents> orderContents;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Order(String address, String city, double totalPrice, User user) {
        this.address = address;
        this.city = city;
        this.totalPrice = totalPrice;
        this.user = user;
    }
}
