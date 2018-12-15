package io.github.sharelison.jsontojava.model;

public class JsonToJavaInstruction implements InstructionDetails{

    private String outputDir;
    private final String json;
    private final String objectName;
    private final String packageName;
    private final boolean withAnnotations;

    /**
     *
     * @param json
     * @param objectName
     * @param packageName
     * @param withAnnotations
     * @param outputDir
     */
    public JsonToJavaInstruction(String json, String objectName, String packageName, boolean withAnnotations, String outputDir) {
        this.json = json;
        this.objectName = objectName;
        this.packageName = packageName;
        this.withAnnotations = withAnnotations;
        this.outputDir = outputDir;
    }

    public JsonToJavaInstruction(String json, String objectName, String packageName, boolean withAnnotations) {
        this.json = json;
        this.objectName = objectName;
        this.packageName = packageName;
        this.withAnnotations = withAnnotations;
    }

    @Override
    public String getOutputDir() {
        return outputDir;
    }

    @Override
    public String getJson() {
        return json;
    }

    @Override
    public String getObjectName() {
        return objectName;
    }

    @Override
    public String getPackageName() {
        return packageName;
    }

    @Override
    public boolean withAnnotations() {
        return withAnnotations;
    }
}
