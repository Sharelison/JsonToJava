package io.github.sharelison.jsontojava.model;

public interface InstructionDetails {
    String getOutputDir();

    String getJson();

    String getObjectName();

    String getPackageName();

    boolean withAnnotations();
}
