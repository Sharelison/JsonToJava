package io.github.sharelison.jsontojava.file;

public interface FileReader {

    /**
     * Extract json from io.github.sharelison.jsontojava.file
     * @param file path to json file
     * @return return contents of file
     */
    String readJsonFromFile(String file);
}
