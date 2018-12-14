package org.edds.opensource.jsontojava.file;

import org.edds.opensource.jsontojava.exception.JsonToJavaException;
import org.junit.Assert;
import org.junit.Test;
import org.edds.opensource.jsontojava.test.JsonToJavaTestConstants;

public class JsonFileReaderTest {

    JsonFileReader jsonFileReader = new JsonFileReader();

    private String newLine = System.lineSeparator();

    @Test
    public void readJsonFromFileShouldReturnExpectedFileContent() {
        //path/path/testfile/exampleObject.json
        String expectedJson = "{"+newLine +
                "  \"field1\": \"asdf df\","+newLine +
                "  \"field4\": 32,"+newLine +
                "  \"exampleObject2\": {"+newLine +
                "    \"field3\": 32,"+newLine +
                "    \"field5\": \"Awoo\""+newLine +
                "  }"+newLine +
                "}";

        String actualJson = jsonFileReader.readJsonFromFile(JsonToJavaTestConstants.JSON_OBJECT_FILE_PATH);

        Assert.assertEquals(expectedJson, actualJson);
    }

    @Test(expected = JsonToJavaException.class)
    public void shouldThrowJsonToJavaExceptionForFileNotExists() {
        jsonFileReader.readJsonFromFile("/asdf/path/that/doesnot/exist");
    }
}
