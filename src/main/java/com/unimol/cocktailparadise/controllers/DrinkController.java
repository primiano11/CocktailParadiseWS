package com.unimol.cocktailparadise.controllers;

import com.unimol.cocktailparadise.services.DrinkService;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/drink")
public class DrinkController {

    private DrinkService drinkService = new DrinkService();

    @POST
    @Path("savedrink")
    @Produces(MediaType.APPLICATION_JSON)
    public String saveDrink(@QueryParam("idDrink") int idDrink, @QueryParam("strDrink") String strDrink,
                            @QueryParam("strCategory") String strCategory, @QueryParam("strAlcoholic") String strAlcoholic,
                            @QueryParam("strGlass") String strGlass, @QueryParam("strInstructionsIT") String strInstructionsIT,
                            @QueryParam("userId") int userId){


        JSONObject jsonObject = new JSONObject();

        if(!(drinkService.isDrinkAlreadyExisting(idDrink, userId))){
            if(drinkService.saveDrink(idDrink, strDrink, strCategory, strAlcoholic, strGlass, strInstructionsIT, userId) == 1){
                jsonObject.put("tag", "saveDrink");
                jsonObject.put("status", true);
                jsonObject.put("message", "Cocktail aggiunto!");
                return jsonObject.toString();

            } else if (drinkService.saveDrink(idDrink, strDrink, strCategory, strAlcoholic, strGlass, strInstructionsIT, userId) == 0) {
                jsonObject.put("tag", "saveDrink");
                jsonObject.put("status", false);
                jsonObject.put("message", "ERRORE");

            }
        } else {

            jsonObject.put("tag", "saveDrink");
            jsonObject.put("status", false);
            jsonObject.put("message", "Hai già salvato questo drink.");
        }

        return jsonObject.toString();

    }
}
