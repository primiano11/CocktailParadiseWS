package com.unimol.cocktailparadise.controllers;

import com.unimol.cocktailparadise.entities.User;
import com.unimol.cocktailparadise.services.UserService;
import com.unimol.cocktailparadise.util.AES;
import com.unimol.cocktailparadise.util.OTPConstants;
import com.unimol.cocktailparadise.util.Utilities;
import org.json.JSONObject;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;

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
        User user = userService.login(mail, password);

        if(user != null){
            jsonObject.put("tag", "login");
            jsonObject.put("status", true);
            jsonObject.put("userId", user.getId());
            jsonObject.put("username", user.getUsername());
            jsonObject.put("message", "Utente trovato");
        }
        else {
            jsonObject.put("tag", "login");
            jsonObject.put("status", false);
            jsonObject.put("userId", 0);
            jsonObject.put("username", "");
            jsonObject.put("message", "Credenziali errate");
        }

        return jsonObject.toString();

    }

    @GET
    @Path("passwordrecovery")
    @Produces
    public String passwordRecovery(@QueryParam("mail") String mail){


        JSONObject jsonObject = new JSONObject();

        String response = "";

        int flag = userService.passwordRecovery(mail);

        if(flag == 0) {

            jsonObject.put("tag", "recoverPassword");
            jsonObject.put("status", false);
            jsonObject.put("otpValue", flag);
            jsonObject.put("message", "Il campo mail non può essere vuoto!");
            response = jsonObject.toString();
            return response;
        }

        if(flag == 1){

            jsonObject.put("tag", "recoverPassword");
            jsonObject.put("status", false);
            jsonObject.put("otpValue", flag);
            jsonObject.put("message", "Il campo non può essere vuoto!");

            response = jsonObject.toString();
            return response;
        }

        if ((flag > 0)) {

            jsonObject.put("tag", "recoverPassword");
            jsonObject.put("status", true);
            jsonObject.put("otpValue", flag);
            jsonObject.put("message", "OTP ok");

            response = jsonObject.toString();
            return response;

        }


        return response;
    }

}
