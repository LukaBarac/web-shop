package com.example.ecommerce_web_shop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "basket")

public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "basket"/*, fetch = FetchType.EAGER*/)
//    @Fetch(value = FetchMode.SUBSELECT)
//    @LazyCollection(LazyCollectionOption.FALSE)
    private List<BasketContents> basketContents;

    public Basket(User user){
        this.user = user;
    }

    @Override
    public String toString() {
        return "Basket{" +
                "id=" + id + "}";
    }

    public Basket(User user, List<BasketContents> basketContents) {
        this.user = user;
        this.basketContents = basketContents;
    }
}
