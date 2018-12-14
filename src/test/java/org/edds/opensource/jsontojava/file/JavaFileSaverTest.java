package org.edds.opensource.jsontojava.file;

import org.junit.Assert;
import org.junit.Test;
import org.edds.opensource.jsontojava.test.JsonToJavaTestConstants;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JavaFileSaverTest {

    JavaFileSaver javaFileSaver = new JavaFileSaver();

    @Test
    public void shouldSaveFileCorrectlyForValidParams() throws IOException {
        String objectName = "ObjectNameIsThis";
        String expectedContent = "public class ObjectNameIsThis { }";
        javaFileSaver.saveJavaFile(expectedContent, objectName, JsonToJavaTestConstants.OUTPUT_FOLDER);
        String expectedFileName = objectName + ".java";

        File expectedFile = new File(JsonToJavaTestConstants.OUTPUT_FOLDER, expectedFileName);

        Assert.assertTrue(expectedFile.exists());
        Assert.assertEquals(expectedContent, new String(Files.readAllBytes(Paths.get(expectedFile.getAbsolutePath()))));
        Assert.assertTrue(expectedFile.delete());
    }
}
