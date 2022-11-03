package com.unimol.cocktailparadise.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "drink")
@Data
@NoArgsConstructor
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

    @Column(name = "strInstructionsIT",  length = 1000)
    private String strInstructionsIT;

    @Column(name = "strDrinkThumb")
    private String strDrinkThumb;

    @Column(name="userId", insertable=false, updatable=false)
    private int userId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    public Drink(int idDrink, String strDrink, String strCategory, String strAlcoholic, String strGlass, String strInstructionsIT, String strDrinkThumb){
        this.idDrink = idDrink;
        this.strDrink = strDrink;
        this.strCategory = strCategory;
        this.strAlcoholic = strAlcoholic;
        this.strGlass = strGlass;
        this.strInstructionsIT = strInstructionsIT;
        this.strDrinkThumb = strDrinkThumb;
    }

}
