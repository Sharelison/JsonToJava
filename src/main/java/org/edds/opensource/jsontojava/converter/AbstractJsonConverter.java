package org.edds.opensource.jsontojava.converter;

import org.edds.opensource.jsontojava.converter.builder.JavaClassBuilder;
import org.edds.opensource.jsontojava.converter.builder.enums.ComplexPropertyType;
import org.edds.opensource.jsontojava.converter.builder.enums.PropertyType;
import org.edds.opensource.jsontojava.converter.builder.enums.SinglePropertyType;
import org.edds.opensource.jsontojava.converter.builder.enums.util.PropertyTypeFinder;
import org.edds.opensource.jsontojava.exception.JsonToJavaException;
import org.edds.opensource.jsontojava.validator.JsonTypeChecker;
import org.edds.opensource.jsontojava.validator.JsonValidator;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractJsonConverter implements JsonConverter{

    protected abstract JsonValidator jsonValidator();

    protected abstract JsonTypeChecker jsonTypeChecker();

    protected List<JsonClassResult> convertJsonToJava(String json, String objectName, String packageName) {
        if(jsonValidator().isValidJson(json)){
            Map<String, JavaClassBuilder> javaClasses = new HashMap<>();

            convert(javaClasses, json,  objectName, packageName);

            return javaClasses.
                    values().
                    stream().
                    map(jcb -> new JsonClassResult(jcb.getClassName(), jcb.build())).
                    collect(Collectors.toList());
        } else {
            throw new JsonToJavaException("JSON Schema is not valid");
        }
    }

    private JavaClassBuilder convert(Map<String, JavaClassBuilder> javaClasses, String json, String objectName, String packageName) {
        JavaClassBuilder javaClassBuilder = null;

        if(jsonTypeChecker().isObject(json)) {
            javaClassBuilder = convertObject(javaClasses, new JSONObject(json), objectName, packageName);
        } else if(jsonTypeChecker().isArray(json)){
            javaClassBuilder = convertArray(javaClasses, new JSONArray(json), objectName, packageName);
        }

        return javaClassBuilder;
    }

    private JavaClassBuilder convertObject(Map<String, JavaClassBuilder> javaClasses, JSONObject jsonObject, String objectName, String packageName) {
        JavaClassBuilder javaClassBuilder = javaClasses.keySet().contains(objectName) ? javaClasses.get(objectName) : new JavaClassBuilder(objectName, packageName);
        javaClasses.put(objectName, javaClassBuilder);

        for(String key : jsonObject.keySet()) {
            Object value = jsonObject.get(key);
            if(value != null && !"null".equals(value.toString())) {
                PropertyType propertyType = PropertyTypeFinder.getPropertyType(value, jsonTypeChecker());
                if(!SinglePropertyType.NEW.equals(propertyType)) {
                    if(!javaClassBuilder.hasProperty(key)) {
                        addProperty(javaClasses, javaClassBuilder, key, propertyType, value, packageName);
                    }
                } else {
                    JavaClassBuilder propertyJavaNewClass = convert(javaClasses, value.toString(), JavaClassBuilder.firstCharToUpperCase(key), packageName);
                    javaClassBuilder.addProperty(key, propertyJavaNewClass.getClassName());
                }
            }
        }

        return javaClassBuilder;
    }

    private JavaClassBuilder convertArray(Map<String, JavaClassBuilder> javaClasses, JSONArray jsonArray, String objectName, String packageName) {
        JavaClassBuilder javaClassBuilder = null;

        for(int i = 0; i < jsonArray.length(); i++) {
            javaClassBuilder = convert(javaClasses, jsonArray.get(i).toString(),  objectName, packageName);
        }

        return javaClassBuilder;
    }

    private void addProperty(Map<String, JavaClassBuilder> javaClasses, JavaClassBuilder javaClassBuilder, String key, PropertyType propertyType, Object value, String packageName) {
        if(propertyType instanceof ComplexPropertyType) {
            ComplexPropertyType complexPropertyType = (ComplexPropertyType) propertyType;
            javaClassBuilder.addProperty(key, complexPropertyType, findGenericForList(javaClasses, key, value, packageName));
            if(complexPropertyType.hasToImport()) {
               javaClassBuilder.addImportStatement(complexPropertyType.getFqName());
            }
        } else {
            javaClassBuilder.addProperty(key, propertyType.getDeclareName());
        }
    }

    protected String findGenericForList(Map<String, JavaClassBuilder> javaClasses, String key, Object value, String packageName) {
        String type = "Object";

        HashSet<String> types = new HashSet<>();

        JSONArray jsonArray = new JSONArray(value.toString());
        for(int i = 0; i < jsonArray.length(); i++) {
            PropertyType propertyType = PropertyTypeFinder.getPropertyType(jsonArray.get(i), jsonTypeChecker());
            if(SinglePropertyType.NEW.equals(propertyType)) {
                String className = JavaClassBuilder.firstCharToUpperCase(key);
                convert(javaClasses, value.toString(), className, packageName);
                types.add(className);
            } else {
                types.add(propertyType.getDeclareName());
                if(types.size() > 1) {
                    return type;
                }
            }
        }

        if(types.size() == 1) {
            type = types.iterator().next();
        }
        return type;
    }
}
