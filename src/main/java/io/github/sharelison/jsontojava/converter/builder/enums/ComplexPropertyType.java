package io.github.sharelison.jsontojava.converter.builder.enums;

public enum ComplexPropertyType implements PropertyType {
  
    LIST("List<%s>", "java.util.List", true);

    private final String declareName;
    private final String fqName;
    private final boolean hasToImport;

    ComplexPropertyType(String declareName, String fqName, boolean hasToImport) {
        this.declareName = declareName;
        this.fqName = fqName;
        this.hasToImport = hasToImport;
    }

    @Override
    public String getDeclareName() {
        return declareName;
    }

    public String getFqName() {
        return fqName;
    }

    public boolean hasToImport() {
        return hasToImport;
    }
}
