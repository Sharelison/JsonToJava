package com.edds.opensource.jsontojava.converter.builder;

import com.edds.opensource.jsontojava.converter.builder.enums.ComplexPropertyType;
import com.edds.opensource.jsontojava.converter.builder.enums.SinglePropertyType;

import java.util.HashSet;

public class JavaClassBuilder {

    private static final char END_STATEMENT = ';';
    private static final String NEW_LINE = System.lineSeparator();
    private static final String SPACE = " ";
    private static final String BIG_SPACE = "    ";
    private static final String DOUBLE_NEW_LINE = System.lineSeparator() + System.lineSeparator();
    private static final char BLOCK_OPEN = '{';
    private static final char BLOCK_CLOSED = '}';
    private static final String METHOD_NO_ARGS = "()";
    private static final char METHOD_OPEN = '(';
    private static final char METHOD_CLOSED = ')';

    private String javaClassDeclaration;

    private StringBuilder properties;
    private StringBuilder gettersAndSetters;
    private StringBuilder importStatements;

    private HashSet<String> importedClasses;

    private HashSet<String> propertyKeyNames;

    public JavaClassBuilder(String className, String packagename) {
        declareClass(className, packagename);
        properties = new StringBuilder();
        gettersAndSetters = new StringBuilder();
        importStatements = new StringBuilder();
        importedClasses = new HashSet<>();
        propertyKeyNames = new HashSet<>();
    }

    public String build() {
        return String.format(javaClassDeclaration, importStatements.toString(), properties.toString(), gettersAndSetters.toString());
    }

    public void addProperty(String propertyName, String declareName) {
        properties
                .append(BIG_SPACE)
                .append("private ")
                .append(declareName)
                .append(SPACE)
                .append(propertyName)
                .append(END_STATEMENT)
                .append(NEW_LINE);
        addGettersAndSetters(propertyName, declareName);
        propertyKeyNames.add(propertyName);
    }

    public void addProperty(String propertyName, ComplexPropertyType complexPropertyType, String genericType) {
        String declareName = String.format(complexPropertyType.getDeclareName(), genericType);
        properties
                .append(BIG_SPACE)
                .append("private ")
                .append(declareName)
                .append(" ")
                .append(propertyName)
                .append(END_STATEMENT)
                .append(NEW_LINE);
        addGettersAndSetters(propertyName, declareName);
    }

    public void addImportStatement(String importStatement) {
        if(!importedClasses.contains(importStatement)) {
            importStatements.append("import ").append(importStatement).append(END_STATEMENT).append(DOUBLE_NEW_LINE);
            importedClasses.add(importStatement);
        }
    }

    public static String firstCharToUpperCase(String propertyName) {
        return propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
    }

    private void declareClass(String className, String packagename) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append("package ").append(packagename).append(END_STATEMENT)
                .append(DOUBLE_NEW_LINE)
                .append("%s") //import statements
                .append("public class ").append(className)
                .append(BLOCK_OPEN)
                .append(DOUBLE_NEW_LINE)
                .append("%s") //properties
                .append(NEW_LINE)
                .append("%s") //getters and setters
                .append(BLOCK_CLOSED);

        javaClassDeclaration = stringBuilder.toString();
    }

    private void addGettersAndSetters(String propertyName, String propertyType) {
        String firstUpperProperty = firstCharToUpperCase(propertyName);
        gettersAndSetters
                .append(BIG_SPACE)
                .append("public ")
                .append(propertyType).append(SPACE)
                .append("get").append(firstUpperProperty).append(METHOD_NO_ARGS)
                .append(BLOCK_OPEN).append(NEW_LINE).append(BIG_SPACE).append(BIG_SPACE)
                .append("return this.").append(propertyName).append(END_STATEMENT)
                .append(NEW_LINE).append(BIG_SPACE)
                .append(BLOCK_CLOSED)
                .append(DOUBLE_NEW_LINE);

        gettersAndSetters
                .append(BIG_SPACE)
                .append("public void").append(SPACE)
                .append("set").append(firstUpperProperty)
                .append(METHOD_OPEN).append(propertyType).append(SPACE).append(propertyName).append(METHOD_CLOSED)
                .append(BLOCK_OPEN).append(NEW_LINE).append(BIG_SPACE).append(BIG_SPACE)
                .append("this.").append(propertyName).append(" = ").append(propertyName).append(END_STATEMENT)
                .append(NEW_LINE).append(BIG_SPACE)
                .append(BLOCK_CLOSED)
                .append(DOUBLE_NEW_LINE);
    }
}
