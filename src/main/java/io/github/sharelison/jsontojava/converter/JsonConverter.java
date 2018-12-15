package io.github.sharelison.jsontojava.converter;

import io.github.sharelison.jsontojava.model.JsonClassResult;

import java.util.List;

public interface JsonConverter {

    List<JsonClassResult> convertToJava(String json, String objectName, String packageName, boolean withAnnotations);
    List<JsonClassResult> convertToJava(String json, String objectName, String packageName);
}
