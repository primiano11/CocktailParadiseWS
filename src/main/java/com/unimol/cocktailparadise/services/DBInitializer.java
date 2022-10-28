package com.unimol.cocktailparadise.services;

import com.unimol.cocktailparadise.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;

public class DBInitializer {

    public String dbInitialize(){
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return "OK";
    }
}
