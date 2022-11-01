package com.unimol.cocktailparadise.controllers;

import com.unimol.cocktailparadise.services.UserService;
import com.unimol.cocktailparadise.util.Utilities;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/user")
public class UserController {

    private UserService userService = new UserService();

    @POST
    @Path("register")
    @Produces(MediaType.APPLICATION_JSON)
    public String register(@QueryParam("username") String username, @QueryParam("mail") String mail, @QueryParam("password") String password){
        String response = "";

        if (userService.registerUser(username, mail, password) == 2){
            response = Utilities.constructJSON("register", true);
            return response;
        }

        if (userService.registerUser(username, mail, password) == 0){
            response = Utilities.constructJSONMessage("register", false, "ERRORE: Compilare tutti i campi!");
            return response;
        }

        if (userService.registerUser(username, mail, password) == 1){
            response = Utilities.constructJSONMessage("register", false, "Sei già registrato!");
            return response;
        }



        return response;
    }


}
