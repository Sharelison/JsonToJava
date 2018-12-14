package org.edds.opensource.jsontojava.converter;

import org.edds.opensource.jsontojava.file.FileReader;
import org.edds.opensource.jsontojava.file.JsonFileReader;
import org.edds.opensource.jsontojava.validator.InputJsonValidator;
import org.edds.opensource.jsontojava.validator.JsonType;
import org.edds.opensource.jsontojava.validator.JsonTypeChecker;
import org.edds.opensource.jsontojava.validator.JsonValidator;

import java.util.List;

public class FileJsonConverter extends AbstractJsonConverter{

    private final FileReader fileReader;
    private final JsonValidator jsonValidator;
    private final JsonTypeChecker jsonTypeChecker;

    /***
     * Defaults
     * @param fileReader default: {@link org.edds.opensource.jsontojava.file.JsonFileReader}
     * @param jsonValidator default: {@link org.edds.opensource.jsontojava.validator.InputJsonValidator}
     * @param jsonTypeChecker default: {@link org.edds.opensource.jsontojava.validator.JsonType}
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
    public List<JsonClassResult> convertToJava(String json, String objectName, String packageName) {
        String jsonString = fileReader.readJsonFromFile(json);
        return convertJsonToJava(jsonString, objectName, packageName);
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
