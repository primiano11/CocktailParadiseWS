package com.unimol.cocktailparadise.controllers;

import com.unimol.cocktailparadise.services.DrinkService;
import org.json.JSONObject;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
                            @QueryParam("user_id") int user_id){


        JSONObject jsonObject = new JSONObject();

        if(drinkService.saveDrink(idDrink, strDrink, strCategory, strAlcoholic, strGlass, strInstructionsIT, user_id) == 1){
            jsonObject.put("tag", "saveDrink");
            jsonObject.put("status", true);
            jsonObject.put("message", "Cocktail aggiunto!");
        }

        return jsonObject.toString();

    }

}
