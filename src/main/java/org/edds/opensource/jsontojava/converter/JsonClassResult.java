package org.edds.opensource.jsontojava.converter;

public class JsonClassResult {

    private String className;
    private String classDeclaration;

    public JsonClassResult(String className, String classDeclaration) {
        this.className = className;
        this.classDeclaration = classDeclaration;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassDeclaration() {
        return classDeclaration;
    }

    public void setClassDeclaration(String classDeclaration) {
        this.classDeclaration = classDeclaration;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Java class result for: ")
                .append(className)
                .append(System.lineSeparator()).append(System.lineSeparator())
                .append(classDeclaration)
                .toString();
    }
}
