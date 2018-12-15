package io.github.sharelison.jsontojava.file;

public interface FileSaver {

    /**
     * Save java code path io.github.sharelison.jsontojava.file
     * @param java Java class definition
     * @param objectName class name
     * @param outputFolder directory to save file
     */
    void saveJavaFile(String java, String objectName, String outputFolder);
}
