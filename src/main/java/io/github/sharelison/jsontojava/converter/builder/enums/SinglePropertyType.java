package io.github.sharelison.jsontojava.converter.builder.enums;

public enum SinglePropertyType implements PropertyType {

    NEW(null),
    STRING("String"),
    INTEGER("Integer"),
    DOUBLE("Double"),
    BOOLEAN("boolean"),
    OBJECT("Object");

    private final String declareName;

    SinglePropertyType(String declareName) {
       this.declareName = declareName;
    }

    @Override
    public String getDeclareName(){
        return declareName;
    }
}
