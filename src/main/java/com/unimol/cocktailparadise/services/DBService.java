package com.unimol.cocktailparadise.services;

import com.unimol.cocktailparadise.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class DBService {

    public String dbRefresh() {

        Session session = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.close();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
        return "OK";
    }
}
