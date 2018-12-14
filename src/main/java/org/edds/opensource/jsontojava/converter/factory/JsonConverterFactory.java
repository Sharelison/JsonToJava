package org.edds.opensource.jsontojava.converter.factory;

import org.edds.opensource.jsontojava.converter.FileJsonConverter;
import org.edds.opensource.jsontojava.converter.JsonConverter;
import org.edds.opensource.jsontojava.converter.StringJsonConverter;
import org.edds.opensource.jsontojava.file.FileReader;
import org.edds.opensource.jsontojava.validator.JsonTypeChecker;
import org.edds.opensource.jsontojava.validator.JsonValidator;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class JsonConverterFactory {

    private final FileReader fileReader;
    private final JsonValidator jsonValidator;
    private final JsonTypeChecker jsonTypeChecker;

    private Set<String> supportedExtensions = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("json", "txt")));

    public JsonConverterFactory(FileReader fileReader, JsonValidator jsonValidator, JsonTypeChecker jsonTypeChecker) {
        this.fileReader = fileReader;
        this.jsonValidator = jsonValidator;
        this.jsonTypeChecker = jsonTypeChecker;
    }
    
    public JsonConverter createJsonConverter(String json) {
        JsonConverter jsonConverter;

        if(hasExtension(json)) {
            jsonConverter = new FileJsonConverter(fileReader, jsonValidator, jsonTypeChecker);
        } else {
            jsonConverter = new StringJsonConverter(jsonValidator, jsonTypeChecker);
        }

        return jsonConverter;
    }

    private boolean hasExtension(String json) {
        String extension = json.substring(json.lastIndexOf('.') + 1);
        return !extension.isEmpty() && supportedExtensions.contains(extension);
    }
}
