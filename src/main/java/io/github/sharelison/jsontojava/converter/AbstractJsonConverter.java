package io.github.sharelison.jsontojava.converter;

import io.github.sharelison.jsontojava.converter.builder.JavaClassBuilder;
import io.github.sharelison.jsontojava.converter.builder.enums.ComplexPropertyType;
import io.github.sharelison.jsontojava.converter.builder.enums.PropertyType;
import io.github.sharelison.jsontojava.converter.builder.enums.SinglePropertyType;
import io.github.sharelison.jsontojava.converter.builder.enums.util.PropertyTypeFinder;
import io.github.sharelison.jsontojava.exception.JsonToJavaException;
import io.github.sharelison.jsontojava.model.JsonClassResult;
import io.github.sharelison.jsontojava.validator.JsonTypeChecker;
import io.github.sharelison.jsontojava.validator.JsonValidator;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractJsonConverter implements JsonConverter{

    protected abstract JsonValidator jsonValidator();

    protected abstract JsonTypeChecker jsonTypeChecker();

    protected List<JsonClassResult> convertJsonToJava(String json, String objectName, String packageName, boolean withAnnotations) {
        if(jsonValidator().isValidJson(json)){
            Map<String, JavaClassBuilder> javaClasses = new HashMap<>();

            convert(javaClasses, json,  objectName, packageName, withAnnotations);

            return javaClasses.
                    values().
                    stream().
                    map(jcb -> new JsonClassResult(jcb.getClassName(), jcb.build())).
                    collect(Collectors.toList());
        } else {
            throw new JsonToJavaException("JSON Schema is not valid");
        }
    }

    private JavaClassBuilder convert(Map<String, JavaClassBuilder> javaClasses, String json, String objectName, String packageName, boolean withAnnotations) {
        JavaClassBuilder javaClassBuilder = null;

        if(jsonTypeChecker().isObject(json)) {
            javaClassBuilder = convertObject(javaClasses, new JSONObject(json), objectName, packageName, withAnnotations);
        } else if(jsonTypeChecker().isArray(json)){
            javaClassBuilder = convertArray(javaClasses, new JSONArray(json), objectName, packageName, withAnnotations);
        }

        return javaClassBuilder;
    }

    private JavaClassBuilder convertObject(Map<String, JavaClassBuilder> javaClasses, JSONObject jsonObject, String objectName, String packageName, boolean withAnnotations) {
        JavaClassBuilder javaClassBuilder = javaClasses.keySet().contains(objectName) ? javaClasses.get(objectName) : new JavaClassBuilder(objectName, packageName, withAnnotations);
        javaClasses.put(objectName, javaClassBuilder);

        for(String key : jsonObject.keySet()) {
            Object value = jsonObject.get(key);
            if(value != null && !"null".equals(value.toString())) {
                PropertyType propertyType = PropertyTypeFinder.getPropertyType(value, jsonTypeChecker());
                if(!SinglePropertyType.NEW.equals(propertyType)) {
                    if(!javaClassBuilder.hasProperty(key)) {
                        addProperty(javaClasses, javaClassBuilder, key, propertyType, value, packageName, withAnnotations);
                    }
                } else {
                    JavaClassBuilder propertyJavaNewClass = convert(javaClasses, value.toString(), JavaClassBuilder.firstCharToUpperCase(key), packageName, withAnnotations);
                    javaClassBuilder.addProperty(key, propertyJavaNewClass.getClassName());
                }
            } else {
                javaClassBuilder.addProperty(key, SinglePropertyType.OBJECT.getDeclareName());
            }
        }

        return javaClassBuilder;
    }

    private JavaClassBuilder convertArray(Map<String, JavaClassBuilder> javaClasses, JSONArray jsonArray, String objectName, String packageName, boolean withAnnotations) {
        JavaClassBuilder javaClassBuilder = null;

        for(int i = 0; i < jsonArray.length(); i++) {
            javaClassBuilder = convert(javaClasses, jsonArray.get(i).toString(),  objectName, packageName, withAnnotations);
        }

        return javaClassBuilder;
    }

    private void addProperty(Map<String, JavaClassBuilder> javaClasses, JavaClassBuilder javaClassBuilder, String key, PropertyType propertyType, Object value, String packageName, boolean withAnnotations) {
        if(propertyType instanceof ComplexPropertyType) {
            ComplexPropertyType complexPropertyType = (ComplexPropertyType) propertyType;
            javaClassBuilder.addProperty(key, complexPropertyType, findGenericForList(javaClasses, key, value, packageName, withAnnotations));
            if(complexPropertyType.hasToImport()) {
               javaClassBuilder.addImportStatement(complexPropertyType.getFqName());
            }
        } else {
            javaClassBuilder.addProperty(key, propertyType.getDeclareName());
        }
    }

    protected String findGenericForList(Map<String, JavaClassBuilder> javaClasses, String key, Object value, String packageName, boolean withAnnotations) {
        String type = SinglePropertyType.OBJECT.getDeclareName();

        HashSet<String> types = new HashSet<>();

        JSONArray jsonArray = new JSONArray(value.toString());
        for(int i = 0; i < jsonArray.length(); i++) {
            PropertyType propertyType = PropertyTypeFinder.getPropertyType(jsonArray.get(i), jsonTypeChecker());
            if(SinglePropertyType.NEW.equals(propertyType)) {
                String className = JavaClassBuilder.firstCharToUpperCase(key);
                convert(javaClasses, value.toString(), className, packageName, withAnnotations);
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
