package com.unimol.cocktailparadise.services;

import com.unimol.cocktailparadise.entities.Drink;
import com.unimol.cocktailparadise.entities.User;
import com.unimol.cocktailparadise.util.HibernateUtil;
import com.unimol.cocktailparadise.util.Utilities;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import java.util.List;

public class DrinkService {

    Transaction transaction = null;


    public int saveDrink(int idDrink, String strDrink, String strCategory, String strAlcoholic, String strGlass, String strInstructionsIT, String strDrinkThumb, int userId) {

        int flag = 0;

        Session session = null;


        try {

            session = HibernateUtil.getSessionFactory().openSession();

            Drink newDrink = new Drink(idDrink, strDrink, strCategory, strAlcoholic, strGlass, strInstructionsIT, strDrinkThumb);
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


    public Boolean isDrinkAlreadyExisting(int idDrink, int userId){

        Boolean flag = false;
        Session session = null;

        session = HibernateUtil.getSessionFactory().openSession();

        Criteria criteria = session.createCriteria(User.class);
        Criterion userIdCr = Restrictions.eq("id", userId);
        criteria.add(userIdCr);
        User user = (User) criteria.uniqueResult();

        List<Drink> userDrinks = user.getDrinks();
        for (Drink d:userDrinks) {
            if(d.getIdDrink() == idDrink){
                return true;
            }
        }
        session.close();

        return flag;
    }

    public void deleteDrink(int idDrink, int userId){

        Session session = null;
        int offsetToDelete = 0;


        session = HibernateUtil.getSessionFactory().openSession();

        Criteria criteria = session.createCriteria(User.class);
        Criterion userIdCr = Restrictions.eq("id", userId);
        criteria.add(userIdCr);
        User user = (User) criteria.uniqueResult();

        List<Drink> userDrinks = user.getDrinks();
        for (Drink d:userDrinks) {
            if(d.getIdDrink() == idDrink){
                offsetToDelete = d.getId();
            }
        }

        transaction = session.beginTransaction();
        String hql = "delete from Drink where id='" + offsetToDelete + "'";
        int deletedDrink = session.createQuery(hql).executeUpdate();
        transaction.commit();
        session.close();

    }

    public List<Drink> getAllDrinks(int userId){

        Session session = null;
        int offsetToDelete = 0;


        session = HibernateUtil.getSessionFactory().openSession();

        Criteria criteria = session.createCriteria(User.class);
        Criterion userIdCr = Restrictions.eq("id", userId);
        criteria.add(userIdCr);
        User user = (User) criteria.uniqueResult();
        session.close();

        return user.getDrinks();

    }

    public void deleteAllDrinks(int userId){

        Session session = null;

        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        String hql = "delete from Drink where userId='" + userId + "'";
        int deletedDrink = session.createQuery(hql).executeUpdate();
        transaction.commit();
        session.close();

    }

}
