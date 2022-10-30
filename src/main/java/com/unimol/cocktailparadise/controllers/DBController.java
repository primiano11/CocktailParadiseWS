package com.unimol.cocktailparadise.controllers;

import com.unimol.cocktailparadise.services.DBService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/db")
public class DBController {

    private DBService dbInitializer = new DBService();

    @GET
    @Path("refresh")
    @Produces(MediaType.TEXT_PLAIN)
    public String refresh(){
        return dbInitializer.dbRefresh();
    }
}
