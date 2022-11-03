package com.unimol.cocktailparadise.controllers;

import com.unimol.cocktailparadise.services.UserService;
import com.unimol.cocktailparadise.util.AES;
import com.unimol.cocktailparadise.util.Utilities;
import org.json.JSONObject;

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

        password = AES.encrypt(password, userService.encKey);

        if (userService.registerUser(username, mail, password) == 2){
            response = Utilities.constructJSONMessage("register", true, "Registrazione avvenuta con successo");
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

    @GET
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    public String login(@QueryParam("mail") String mail, @QueryParam("password") String password){

        JSONObject jsonObject = new JSONObject();
        int flag = userService.login(mail, password);

        if(flag != 0){
            jsonObject.put("tag", "login");
            jsonObject.put("status", true);
            jsonObject.put("userId", flag);
            jsonObject.put("message", "Utente trovato");
        }
        else {
            jsonObject.put("tag", "login");
            jsonObject.put("status", false);
            jsonObject.put("userId", flag);
            jsonObject.put("message", "Credenziali errate");
        }

        return jsonObject.toString();

    }

}
