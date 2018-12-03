package com.edds.opensource.jsontojava.validator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonType implements JsonTypeChecker {

    public boolean isObject(String json) {
        boolean success = true;

        try {
            JSONObject jsonObject = new JSONObject(json);
        } catch (JSONException jsonException) {
            success = false;
        }

        return success;
    }

    public boolean isArray(String json) {
        boolean success = true;

        try {
            JSONArray jsonArray = new JSONArray(json);
        } catch (JSONException jsonException) {
            success = false;
        }

        return success;
    }
}

