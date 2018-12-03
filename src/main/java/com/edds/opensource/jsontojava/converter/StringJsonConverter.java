package com.edds.opensource.jsontojava.converter;

import com.edds.opensource.jsontojava.validator.InputJsonValidator;
import com.edds.opensource.jsontojava.validator.JsonType;
import com.edds.opensource.jsontojava.validator.JsonTypeChecker;
import com.edds.opensource.jsontojava.validator.JsonValidator;

import java.util.List;

public class StringJsonConverter extends AbstractJsonConverter{

    private final JsonValidator jsonValidator;
    private final JsonTypeChecker jsonTypeChecker;

    /**
     *
     * @param jsonValidator default: {@link com.edds.opensource.jsontojava.validator.InputJsonValidator}
     * @param jsonTypeChecker default: {@link com.edds.opensource.jsontojava.validator.JsonType}
     */
    public StringJsonConverter(JsonValidator jsonValidator, JsonTypeChecker jsonTypeChecker) {
        this.jsonValidator = jsonValidator;
        this.jsonTypeChecker = jsonTypeChecker;
    }

    public StringJsonConverter() {
        jsonValidator = new InputJsonValidator();
        jsonTypeChecker = new JsonType();
    }

    /**
     * converts a json string to a java class (and child classes)
     * given a json that contains an array the first item in the array will be parsed
     * @param json
     * @param objectName
     * @return
     */
    @Override
    public List<JsonClassResult> convertToJava(String json, String objectName, String packageName) {
        return convertJsonToJava(json, objectName, packageName);

    }

    @Override
    protected JsonValidator jsonValidator() {
        return jsonValidator;
    }

    @Override
    protected JsonTypeChecker jsonTypeChecker() {
        return jsonTypeChecker;
    }
}
