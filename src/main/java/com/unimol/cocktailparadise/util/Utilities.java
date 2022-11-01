package com.unimol.cocktailparadise.util;

import org.json.JSONException;
import org.json.JSONObject;

public class Utilities {

    public static boolean isNotNull(String txt) {
        return txt != null && txt.trim().length() > 0 ? true : false;
    }

    public static String constructJSONMessage(String tag, boolean status,String err_msg) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("tag", tag);
            obj.put("status", new Boolean(status));
            obj.put("error_msg", err_msg);
        } catch (JSONException jsonException) {
            System.err.println(jsonException.getMessage());
        }
        return obj.toString();
    }

    public static String constructJSON(String tag, boolean status) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("tag", tag);
            jsonObject.put("status", new Boolean(status));
        } catch (JSONException jsonException) {
            System.err.println(jsonException.getMessage());
        }
        return jsonObject.toString();
    }

}
