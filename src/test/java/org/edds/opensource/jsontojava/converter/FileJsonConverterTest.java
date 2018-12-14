package org.edds.opensource.jsontojava.converter;

import org.edds.opensource.jsontojava.file.FileReader;
import org.edds.opensource.jsontojava.validator.JsonTypeChecker;
import org.edds.opensource.jsontojava.validator.JsonValidator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.edds.opensource.jsontojava.test.JsonToJavaTestConstants;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class FileJsonConverterTest {

    @Mock
    private FileReader fileReader;

    @Mock
    private JsonValidator jsonValidator;

    @Mock
    private JsonTypeChecker jsonTypeChecker;

    @InjectMocks
    private FileJsonConverter jsonConverter;

    @Before
    public void doBeofre() throws IOException {
        Mockito.when(jsonValidator.isValidJson(Mockito.anyString())).thenReturn(true);
    }

    @After
    public void verifyMockCalledAfter() {
        Mockito.verify(fileReader, Mockito.times(1)).readJsonFromFile(Mockito.anyString());
    }

    @Test
    public void shouldReturnJavaClassForJsonObject() throws IOException {
        Mockito.when(fileReader.readJsonFromFile(Mockito.anyString())).thenReturn(new String(Files.readAllBytes(Paths.get(jsonObject()))));
        Mockito.when(jsonTypeChecker.isObject(Mockito.anyString())).thenReturn(true);
        Mockito.when(jsonTypeChecker.isArray(Mockito.anyString())).thenReturn(false);

        List<JsonClassResult> javaClasses = jsonConverter.convertToJava(jsonObject(), JsonToJavaTestConstants.JSON_OBJECT_NAME,"com.package");

        Assert.assertNotNull(javaClasses);
        Assert.assertFalse(javaClasses.isEmpty());
    }

    @Test
    public void shouldReturnClassForObjectsInListForJsonArray() throws IOException {
        Mockito.when(fileReader.readJsonFromFile(Mockito.anyString())).thenReturn(new String(Files.readAllBytes(Paths.get(jsonArray()))));
        Mockito.when(jsonTypeChecker.isObject(Mockito.anyString())).thenReturn(false).thenReturn(true);
        Mockito.when(jsonTypeChecker.isArray(Mockito.anyString())).thenReturn(true).thenReturn(false);
        List<JsonClassResult> json = jsonConverter.convertToJava(jsonArray(), JsonToJavaTestConstants.JSON_OBJECT_NAME,"com.package");

        Assert.assertNotNull(json);
        Assert.assertFalse(json.isEmpty());
    }

    protected String jsonObject() {
        return JsonToJavaTestConstants.JSON_OBJECT_FILE_PATH;
    }

    protected String jsonArray() {
        return JsonToJavaTestConstants.JSON_ARRAY_FILE_PATH;
    }
}
