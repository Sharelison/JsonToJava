package org.edds.opensource.jsontojava.validator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonType implements JsonTypeChecker {

    public boolean isObject(String json) {
        boolean success = true;

        try {
            new JSONObject(json);
        } catch (JSONException jsonException) {
            success = false;
        }

        return success;
    }

    public boolean isArray(String json) {
        boolean success = true;

        try {
            new JSONArray(json);
        } catch (JSONException jsonException) {
            success = false;
        }

        return success;
    }
}

