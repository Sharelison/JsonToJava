package org.edds.opensource.jsontojava.converter;

import java.util.List;

public interface JsonConverter {

    List<JsonClassResult> convertToJava(String json, String objectName, String packageName);
}
