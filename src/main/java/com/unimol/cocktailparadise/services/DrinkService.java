package com.unimol.cocktailparadise.services;

import com.unimol.cocktailparadise.entities.Drink;
import com.unimol.cocktailparadise.entities.User;
import com.unimol.cocktailparadise.util.HibernateUtil;
import com.unimol.cocktailparadise.util.Utilities;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class DrinkService {

    Transaction transaction = null;


    public int saveDrink(int idDrink, String strDrink, String strCategory, String strAlcoholic, String strGlass, String strInstructionsIT, int userId) {

        int flag = 0;

        Session session = null;


        try {

            session = HibernateUtil.getSessionFactory().openSession();

            Drink newDrink = new Drink(idDrink, strDrink, strCategory, strAlcoholic, strGlass, strInstructionsIT);
            String hql = "from User where id='" + userId + "'";
            Query query = session.createQuery(hql);
            User user = (User) query.uniqueResult();

            newDrink.setUser(user);

            transaction = session.beginTransaction();
            session.save(newDrink);
            transaction.commit();
            session.close();
            flag = 1;
            return flag;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return flag;
    }


    private Boolean isDrinkAlreadyExisting(int idDrink, int userId){

        Boolean flag = false;
        Session session = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "from Drink where idDrink='" + idDrink + "'" + " and userId='" + userId +"'";
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
