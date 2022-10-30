package com.unimol.cocktailparadise.services;

import com.unimol.cocktailparadise.entities.User;
import com.unimol.cocktailparadise.util.HibernateUtil;
import com.unimol.cocktailparadise.util.Utilities;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;

public class UserService {

    Transaction transaction = null;


    public String registerUser(String username, String mail, String password) {

        String result = "";

        if (!(Utilities.isNotNull(username) && Utilities.isNotNull(mail) && Utilities.isNotNull(password))){
            result = "ERRORE";
            return result;
        }

        Session session = null;

        try {
            User newUser = new User(username, mail, password);
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(newUser);
            result = "OK";
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return result;
    }
}
