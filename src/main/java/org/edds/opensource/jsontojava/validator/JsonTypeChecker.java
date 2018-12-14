package org.edds.opensource.jsontojava.validator;

public interface JsonTypeChecker {

    boolean isObject(String json);

    boolean isArray(String json);
}
