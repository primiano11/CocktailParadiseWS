package com.unimol.cocktailparadise.services;

import com.unimol.cocktailparadise.entities.User;
import com.unimol.cocktailparadise.util.HibernateUtil;
import com.unimol.cocktailparadise.util.Utilities;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class UserService {

    Transaction transaction = null;


    public int registerUser(String username, String mail, String password) {

        int flag = 0;

        if (!(Utilities.isNotNull(username) && Utilities.isNotNull(mail) && Utilities.isNotNull(password))){
            //"ERRORE";
            flag = 0;
            return flag;
        }

        if(isUserAlreadyRegistered(mail)){
            //"Sei già registrato!";
            flag = 1;
            return flag;
        }

        Session session = null;

        try {
            User newUser = new User(username, mail, password);
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(newUser);
            //"OK";
            flag = 2;
            session.close();
            return flag;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return flag;
    }


    public Boolean isUserAlreadyRegistered(String mail){

        Session session = null;

        Boolean flag = false;


        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "from User where mail='" + mail + "'";
            Query query = session.createQuery(hql);
            List results = query.list();

            if(results.size() > 0){
                flag = true;
            }

            session.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return flag;
    }
}
