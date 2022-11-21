package com.example.ecommerce_web_shop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "basket")
/*@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)*/
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(/*fetch = FetchType.LAZY*/)
  //  @JsonIgnore
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "basket"/*, fetch = FetchType.LAZY*/)
    private List<BasketContents> basketContents;

    public Basket(User user){
        this.user = user;
    }

    public Basket(List<BasketContents> basketContents) {
        this.basketContents = basketContents;
    }

    @Override
    public String toString() {
        return "Basket{" +
                "id=" + id + /*"basketContents=" + basketContents +*/ "}";       //izbacio sam basketcontents, nije radilo besa tim (isto kao sto smo brisali usera juce)
    }
                                        // zasto na user u debug izbacuje error kad ubacim basketcontents
    /*@Override
    public String toString() {
        return "Basket{" +
                "id=" + id +
                '}';
    }*/
}
