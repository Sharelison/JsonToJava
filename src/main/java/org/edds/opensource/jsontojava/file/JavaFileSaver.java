package org.edds.opensource.jsontojava.file;

import org.edds.opensource.jsontojava.exception.JsonToJavaException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JavaFileSaver implements FileSaver {

    private static final String JAVA_FILE_EXTENSION = ".java";
    private static final String UNEXPECTED_ERR_MSG = "Unexpected error while trying to save java file";

    @Override
    public void saveJavaFile(String java, String objectName, String outputFolder) {
        try {
            File javaFile = new File(outputFolder, objectName + JAVA_FILE_EXTENSION);
            boolean successfulCreation = javaFile.getParentFile().exists() ? javaFile.createNewFile() : (javaFile.getParentFile().mkdirs() && javaFile.createNewFile());
            if(successfulCreation || javaFile.exists()){
                writeJavaToFile(javaFile, java);
            } else {
                throw new JsonToJavaException(UNEXPECTED_ERR_MSG);
            }
        } catch (IOException ioException) {
            throw new JsonToJavaException("IO error while trying to save file: " + objectName, ioException);
        } catch (RuntimeException runTimeException) {
            throw new JsonToJavaException(UNEXPECTED_ERR_MSG + objectName, runTimeException);
        }
    }

    private void writeJavaToFile(File javaFile, String java) throws IOException {
        try(FileWriter fw = new FileWriter(javaFile)) {
            fw.append(java);
        }
    }
}
