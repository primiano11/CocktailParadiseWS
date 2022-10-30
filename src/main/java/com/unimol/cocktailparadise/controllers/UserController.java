package com.unimol.cocktailparadise.controllers;

import com.unimol.cocktailparadise.services.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/user")
public class UserController {

    private UserService userService = new UserService();

    @POST
    @Path("register")
    @Produces(MediaType.TEXT_PLAIN)
    public String register(@QueryParam("username") String username, @QueryParam("mail") String mail, @QueryParam("password") String password){
        return userService.registerUser(username, mail, password);
    }





}
