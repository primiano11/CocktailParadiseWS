package com.unimol.cocktailparadise.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    @NotNull
    private String username;

    @Column(name = "mail")
    @NotNull
    private String mail;

    @Column(name = "password")
    @NotNull
    private String password;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
    private List<Drink> drinks;

    public User(String username, String mail, String password) {
        this.username = username;
        this.mail = mail;
        this.password = password;
    }

}
