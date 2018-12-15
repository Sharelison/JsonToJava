package io.github.sharelison.jsontojava.converter;

import java.util.List;

public interface JsonConverter {

    List<JsonClassResult> convertToJava(String json, String objectName, String packageName, boolean withAnnotations);

    List<JsonClassResult> convertToJava(String json, String objectName, String packageName);
}
