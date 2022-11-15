package com.unimol.cocktailparadise.controllers;

import com.unimol.cocktailparadise.entities.Drink;
import com.unimol.cocktailparadise.services.DrinkService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/drink")
public class DrinkController {

    private DrinkService drinkService = new DrinkService();

    @POST
    @Path("savedrink")
    @Produces(MediaType.APPLICATION_JSON)
    public String saveDrink(@QueryParam("idDrink") int idDrink, @QueryParam("strDrink") String strDrink,
                            @QueryParam("strCategory") String strCategory, @QueryParam("strAlcoholic") String strAlcoholic,
                            @QueryParam("strGlass") String strGlass, @QueryParam("strInstructionsIT") String strInstructionsIT,
                            @QueryParam("strDrinkThumb") String strDrinkThumb, @QueryParam("userId") int userId) {


        JSONObject jsonObject = new JSONObject();

        if (!(drinkService.isDrinkAlreadyExisting(idDrink, userId))) {
            if (drinkService.saveDrink(idDrink, strDrink, strCategory, strAlcoholic, strGlass, strInstructionsIT, strDrinkThumb, userId) == 1) {
                jsonObject.put("tag", "saveDrink");
                jsonObject.put("status", true);
                jsonObject.put("message", "Cocktail aggiunto!");
                return jsonObject.toString();

            } else if (drinkService.saveDrink(idDrink, strDrink, strCategory, strAlcoholic, strGlass, strInstructionsIT, strDrinkThumb, userId) == 0) {
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

    @POST
    @Path("deletedrink")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteDrink(@QueryParam("idDrink") int idDrink, @QueryParam("userId") int userId) {

        JSONObject jsonObject = new JSONObject();

        if (drinkService.isDrinkAlreadyExisting(idDrink, userId)) {
            drinkService.deleteDrink(idDrink, userId);
            jsonObject.put("tag", "deleteDrink");
            jsonObject.put("status", true);
            jsonObject.put("message", "Hai rimosso il drink.");
            return jsonObject.toString();
        } else {
            jsonObject.put("tag", "deleteDrink");
            jsonObject.put("status", false);
            jsonObject.put("message", "Il drink non esiste.");
            return jsonObject.toString();
        }

    }

    @GET
    @Path("getalldrinks")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllDrinks(@QueryParam("userId") int userId) {

        String response = "";
        JSONObject jsonObject = new JSONObject();
        JSONArray allDataArray = new JSONArray();
        List<Drink> userDrinks = drinkService.getAllDrinks(userId);

        if (!(userDrinks.size() == 0)) {

            for (int index = 0; index < userDrinks.size(); index++) {
                JSONObject eachData = new JSONObject();
                try {
                    eachData.put("id", userDrinks.get(index).getId());
                    eachData.put("idDrink", userDrinks.get(index).getIdDrink());
                    eachData.put("strAlcoholic", userDrinks.get(index).getStrAlcoholic());
                    eachData.put("strCategory", userDrinks.get(index).getStrCategory());
                    eachData.put("strDrink", userDrinks.get(index).getStrDrink());
                    eachData.put("strGlass", userDrinks.get(index).getStrGlass());
                    eachData.put("strInstructionsIT", userDrinks.get(index).getStrInstructionsIT());
                    eachData.put("strDrinkThumb", userDrinks.get(index).getStrDrinkThumb());
                    eachData.put("userId", userDrinks.get(index).getUserId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                allDataArray.put(eachData);
            }
        } else {
            jsonObject.put("tag", "getAllDrinks");
            jsonObject.put("status", false);
            jsonObject.put("message", "Non hai ancora salvato nessun drink");
            response = jsonObject.toString();
            return response;
        }

        try {
            jsonObject.put("drinks", allDataArray);
            response = jsonObject.toString();
            return response;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return response;
    }


    @POST
    @Path("deletealldrinks")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteAllDrinks(@QueryParam("userId") int userId) {

        String response = "";
        JSONObject jsonObject = new JSONObject();

        if (drinkService.getAllDrinks(userId).size() != 0) {
            drinkService.deleteAllDrinks(userId);
            jsonObject.put("tag", "deleteAllDrinks");
            jsonObject.put("status", true);
            jsonObject.put("message", "Hai rimosso tutti i drink");
            response = jsonObject.toString();
            return response;
        } else {
            jsonObject.put("tag", "deleteAllDrinks");
            jsonObject.put("status", false);
            jsonObject.put("message", "ERRORE");
            response = jsonObject.toString();
            return response;
        }

    }


}
