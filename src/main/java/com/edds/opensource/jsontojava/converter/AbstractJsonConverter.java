package com.edds.opensource.jsontojava.converter;

import com.edds.opensource.jsontojava.converter.builder.JavaClassBuilder;
import com.edds.opensource.jsontojava.converter.builder.enums.ComplexPropertyType;
import com.edds.opensource.jsontojava.converter.builder.enums.PropertyType;
import com.edds.opensource.jsontojava.converter.builder.enums.SinglePropertyType;
import com.edds.opensource.jsontojava.converter.builder.enums.util.PropertyTypeFinder;
import com.edds.opensource.jsontojava.exception.JsonToJavaException;
import com.edds.opensource.jsontojava.validator.JsonTypeChecker;
import com.edds.opensource.jsontojava.validator.JsonValidator;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public abstract class AbstractJsonConverter implements JsonConverter{

    protected abstract JsonValidator jsonValidator();

    protected abstract JsonTypeChecker jsonTypeChecker();

    protected List<JsonClassResult> convertJsonToJava(String json, String objectName, String packageName) {
        if(jsonValidator().isValidJson(json)){
            List<JsonClassResult> javaClasses = new ArrayList<>();
            convert(javaClasses, json,  objectName, packageName, new HashMap<String, Integer>());

            return javaClasses;
        } else {
            throw new JsonToJavaException("JSON Schema is not valid");
        }
    }

    private JsonClassResult convert(List<JsonClassResult> javaClasses, String json, String objectName, String packageName, Map<String, Integer> createdClasses) {
        JsonClassResult jsonClassResult = null;

        if(jsonTypeChecker().isObject(json)) {
            jsonClassResult = convertObject(javaClasses, new JSONObject(json), objectName, packageName, createdClasses);
        } else if(jsonTypeChecker().isArray(json)){
            convertArray(javaClasses, new JSONArray(json), objectName, packageName, createdClasses);
        }

        return jsonClassResult;
    }

    private JsonClassResult convertObject(List<JsonClassResult> javaClasses, JSONObject jsonObject, String objectName, String packageName, Map<String, Integer> createdClasses) {
        JavaClassBuilder javaClassBuilder = new JavaClassBuilder(objectName, packageName);
        for(String key : jsonObject.keySet()) {
            Object value = jsonObject.get(key);
            if(value != null && !"null".equals(value.toString())) {
                PropertyType propertyType = PropertyTypeFinder.getPropertyType(value, jsonTypeChecker());
                if(!SinglePropertyType.NEW.equals(propertyType)) {
                    addProperty(javaClasses, javaClassBuilder, key, propertyType, value, packageName, createdClasses);
                }
                else {
                    String className = getClassName(createdClasses, JavaClassBuilder.firstCharToUpperCase(key));
                    JsonClassResult jsonClassResult = convert(javaClasses, value.toString(), className, packageName, createdClasses);
                    if(jsonClassResult != null) {
                        javaClassBuilder.addProperty(key, className);
                    }
                }
            }
        }

        JsonClassResult jsonClassResult = new JsonClassResult(objectName, javaClassBuilder.build());
        javaClasses.add(jsonClassResult);

        return jsonClassResult;
    }

    private void convertArray(List<JsonClassResult> javaClasses, JSONArray jsonArray, String objectName, String packageName, Map<String, Integer> createdClasses) {
        String jsonString = jsonArray.get(0).toString();
        convert(javaClasses, jsonString,  objectName, packageName, createdClasses);
    }

    private void addProperty(List<JsonClassResult> javaClasses, JavaClassBuilder javaClassBuilder, String key, PropertyType propertyType, Object value, String packageName, Map<String, Integer> createdClasses) {
        if(propertyType instanceof ComplexPropertyType) {
            ComplexPropertyType complexPropertyType = (ComplexPropertyType) propertyType;
            javaClassBuilder.addProperty(key, complexPropertyType, findGenericForList(javaClasses, key, value, packageName, createdClasses));
            if(complexPropertyType.hasToImport()) {
               javaClassBuilder.addImportStatement(complexPropertyType.getFqName());
            }
        } else {
            javaClassBuilder.addProperty(key, propertyType.getDeclareName());
        }
    }

    protected String findGenericForList(List<JsonClassResult> javaClasses, String key, Object value, String packageName, Map<String, Integer> createdClasses) {
        String type = "Object";

        HashSet<String> types = new HashSet<>();

        JSONArray jsonArray = new JSONArray(value.toString());
        for(int i = 0; i < jsonArray.length(); i++) {
            PropertyType propertyType = PropertyTypeFinder.getPropertyType(jsonArray.get(i), jsonTypeChecker());
            if(SinglePropertyType.NEW.equals(propertyType)) {
                String className = getClassName(createdClasses, JavaClassBuilder.firstCharToUpperCase(key));
                convert(javaClasses, value.toString(), className, packageName, createdClasses);
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

    private String getClassName(Map<String, Integer> createdClasses, String key) {
        String className = key;

        Integer count = createdClasses.get(key);
        if(count == null){
            createdClasses.put(key, 0);
        } else {
            createdClasses.put(key, ++count);
            className = key + count;
        }

        return className;
    }
}
