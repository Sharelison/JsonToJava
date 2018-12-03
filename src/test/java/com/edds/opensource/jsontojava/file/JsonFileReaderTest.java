package com.edds.opensource.jsontojava.file;

import com.edds.opensource.jsontojava.exception.JsonToJavaException;
import org.junit.Assert;
import org.junit.Test;
import com.edds.opensource.jsontojava.test.JsonToJavaTestConstants;

public class JsonFileReaderTest {

    JsonFileReader jsonFileReader = new JsonFileReader();

    @Test
    public void readJsonFromFileShouldReturnExpectedFileContent() {
        //path/path/testfile/exampleObject.json
        String expectedJson = "{\n" +
                "  \"field1\": \"asdf df\",\n" +
                "  \"field4\": 32,\n" +
                "  \"exampleObject2\": {\n" +
                "    \"field3\": 32,\n" +
                "    \"field5\": \"Awoo\"\n" +
                "  }\n" +
                "}";

        String actualJson = jsonFileReader.readJsonFromFile(JsonToJavaTestConstants.JSON_OBJECT_FILE_PATH);

        Assert.assertEquals(expectedJson, actualJson);
    }

    @Test(expected = JsonToJavaException.class)
    public void shouldThrowJsonToJavaExceptionForFileNotExists() {
        jsonFileReader.readJsonFromFile("/asdf/path/that/doesnot/exist");
    }
}
