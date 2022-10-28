package com.unimol.cocktailparadise.controllers;

import com.unimol.cocktailparadise.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/db")
public class DBController {

    @GET
    @Path("refresh")
    @Produces(MediaType.TEXT_PLAIN)
    public String refresh(){
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
