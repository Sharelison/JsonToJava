package io.github.sharelison.jsontojava.converter;

import io.github.sharelison.jsontojava.validator.InputJsonValidator;
import io.github.sharelison.jsontojava.validator.JsonType;
import io.github.sharelison.jsontojava.validator.JsonTypeChecker;
import io.github.sharelison.jsontojava.validator.JsonValidator;

import java.util.List;

public class StringJsonConverter extends AbstractJsonConverter{

    private final JsonValidator jsonValidator;
    private final JsonTypeChecker jsonTypeChecker;

    /**
     *
     * @param jsonValidator default: {@link io.github.sharelison.jsontojava.validator.InputJsonValidator}
     * @param jsonTypeChecker default: {@link io.github.sharelison.jsontojava.validator.JsonType}
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
