package io.github.sharelison.jsontojava.converter;

import io.github.sharelison.jsontojava.constants.JsonToJavaConstants;
import io.github.sharelison.jsontojava.file.FileReader;
import io.github.sharelison.jsontojava.file.JsonFileReader;
import io.github.sharelison.jsontojava.model.JsonClassResult;
import io.github.sharelison.jsontojava.validator.InputJsonValidator;
import io.github.sharelison.jsontojava.validator.JsonType;
import io.github.sharelison.jsontojava.validator.JsonTypeChecker;
import io.github.sharelison.jsontojava.validator.JsonValidator;

import java.util.List;

public class FileJsonConverter extends AbstractJsonConverter{

    private final FileReader fileReader;
    private final JsonValidator jsonValidator;
    private final JsonTypeChecker jsonTypeChecker;

    /***
     * Defaults
     * @param fileReader default: {@link io.github.sharelison.jsontojava.file.JsonFileReader}
     * @param jsonValidator default: {@link io.github.sharelison.jsontojava.validator.InputJsonValidator}
     * @param jsonTypeChecker default: {@link io.github.sharelison.jsontojava.validator.JsonType}
     */
    public FileJsonConverter(FileReader fileReader, JsonValidator jsonValidator, JsonTypeChecker jsonTypeChecker) {
        this.fileReader = fileReader;
        this.jsonValidator = jsonValidator;
        this.jsonTypeChecker = jsonTypeChecker;
    }

    public FileJsonConverter() {
        this.fileReader = new JsonFileReader();
        this.jsonValidator = new InputJsonValidator();
        this.jsonTypeChecker = new JsonType();
    }

    /**
     * converts a json path (json in given filepath) to a java class (and child classes)
     * given a json that contains an array the first item in the array will be parsed
     * @param json
     * @param objectName
     * @return
     */
    @Override
    public List<JsonClassResult> convertToJava(String json, String objectName, String packageName, boolean withAnnotations) {
        String jsonString = fileReader.readJsonFromFile(json);
        return convertJsonToJava(jsonString, objectName, packageName, withAnnotations);
    }

    @Override
    public List<JsonClassResult> convertToJava(String json, String objectName, String packageName) {
        return convertToJava(json, objectName, packageName, JsonToJavaConstants.DEFAULT_FOR_WITH_ANNOTATIONS);
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
