package io.github.sharelison.jsontojava;

import io.github.sharelison.jsontojava.converter.JsonConverter;
import io.github.sharelison.jsontojava.converter.factory.JsonConverterFactory;
import io.github.sharelison.jsontojava.converter.JsonClassResult;
import io.github.sharelison.jsontojava.exception.JsonToJavaException;
import io.github.sharelison.jsontojava.file.FileReader;
import io.github.sharelison.jsontojava.file.FileSaver;
import io.github.sharelison.jsontojava.file.JavaFileSaver;
import io.github.sharelison.jsontojava.file.JsonFileReader;
import io.github.sharelison.jsontojava.validator.InputJsonValidator;
import io.github.sharelison.jsontojava.validator.JsonType;
import io.github.sharelison.jsontojava.validator.JsonTypeChecker;
import io.github.sharelison.jsontojava.validator.JsonValidator;

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
     * @param fileReader default {@link io.github.sharelison.jsontojava.file.JsonFileReader}
     * @param jsonValidator default {@link io.github.sharelison.jsontojava.validator.InputJsonValidator}
     * @param typeChecker default {@link io.github.sharelison.jsontojava.validator.JsonType}
     * @param fileSaver default {@link io.github.sharelison.jsontojava.file.JavaFileSaver}
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
