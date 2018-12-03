package com.edds.opensource.jsontojava;

import com.edds.opensource.jsontojava.converter.JsonConverter;
import com.edds.opensource.jsontojava.converter.factory.JsonConverterFactory;
import com.edds.opensource.jsontojava.converter.JsonClassResult;
import com.edds.opensource.jsontojava.exception.JsonToJavaException;
import com.edds.opensource.jsontojava.file.FileReader;
import com.edds.opensource.jsontojava.file.FileSaver;
import com.edds.opensource.jsontojava.file.JavaFileSaver;
import com.edds.opensource.jsontojava.file.JsonFileReader;
import com.edds.opensource.jsontojava.validator.InputJsonValidator;
import com.edds.opensource.jsontojava.validator.JsonType;
import com.edds.opensource.jsontojava.validator.JsonTypeChecker;
import com.edds.opensource.jsontojava.validator.JsonValidator;

import java.util.List;

public class JsonToJava {

    private final JsonConverterFactory jsonConverterFactory;
    private final FileSaver fileSaver;
    private JsonConverter jsonConverter;

    public JsonToJava() {
        this.jsonConverterFactory = new JsonConverterFactory(new JsonFileReader(), new InputJsonValidator(new JsonType()), new JsonType());
        this.fileSaver = new JavaFileSaver();
    }

    /**
     *
     * @param fileReader default {@link com.edds.opensource.jsontojava.file.JsonFileReader}
     * @param jsonValidator default {@link com.edds.opensource.jsontojava.validator.InputJsonValidator}
     * @param typeChecker default {@link com.edds.opensource.jsontojava.validator.JsonType}
     * @param fileSaver default {@link com.edds.opensource.jsontojava.file.JavaFileSaver}
     */
    public JsonToJava(FileReader fileReader, JsonValidator jsonValidator, JsonTypeChecker typeChecker, FileSaver fileSaver) {
        this.jsonConverterFactory = new JsonConverterFactory(fileReader, jsonValidator, typeChecker);
        this.fileSaver = fileSaver;
    }

    public JsonToJava(FileReader fileReader, JsonValidator jsonValidator, JsonTypeChecker jsonTypeChecker) {
        this.jsonConverterFactory = new JsonConverterFactory(fileReader, jsonValidator, jsonTypeChecker);
        this.fileSaver = null;
    }

    public void jsonToJava(String json, String objectName, String packageName, String outputDir) {
        initializeJsonConverter(json);

        List<JsonClassResult> javaClassResult = jsonConverter.convertToJava(json, objectName, packageName);
        if(fileSaver != null){
            javaClassResult
                    .parallelStream()
                    .forEach(classResult -> fileSaver.saveJavaFile(classResult.getClassDeclaration(), classResult.getClassName(), outputDir));
        } else {
            throw new JsonToJavaException("No instance of FileSaver found");
        }
    }

    public List<JsonClassResult> jsonToJava(String json, String objectName, String packageName){
        initializeJsonConverter(json);
        return jsonConverter.convertToJava(json, objectName, packageName);
    }

    private synchronized void initializeJsonConverter(String json) {
        if(jsonConverter ==  null) {
            jsonConverter = jsonConverterFactory.createJsonConverter(json);
        }
    }
}
