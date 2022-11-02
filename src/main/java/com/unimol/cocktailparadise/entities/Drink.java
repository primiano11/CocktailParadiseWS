package com.unimol.cocktailparadise.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "drink")
@Data
public class Drink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @Column(name = "idDrink")
    private int idDrink;

    @Column(name = "strDrink")
    private String strDrink;

    @Column(name = "strCategory")
    private String strCategory;

    @Column(name = "strAlcoholic")
    private String strAlcoholic;

    @Column(name = "strGlass")
    private String strGlass;

    @Column(name = "strInstructionsIT")
    private String strInstructionsIT;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    public Drink(int idDrink, String strDrink, String strCategory, String strAlcoholic, String strGlass, String strInstructionsIT){
        this.idDrink = idDrink;
        this.strDrink = strDrink;
        this.strCategory = strCategory;
        this.strAlcoholic = strAlcoholic;
        this.strGlass = strGlass;
        this.strInstructionsIT = strInstructionsIT;
    }

}
