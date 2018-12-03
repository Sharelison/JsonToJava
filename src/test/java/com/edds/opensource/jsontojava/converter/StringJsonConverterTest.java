package com.edds.opensource.jsontojava.converter;

import com.edds.opensource.jsontojava.test.JsonToJavaTestConstants;
import com.edds.opensource.jsontojava.validator.JsonTypeChecker;
import com.edds.opensource.jsontojava.validator.JsonValidator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class StringJsonConverterTest {

    @Mock
    private JsonValidator jsonValidator;

    @Mock
    private JsonTypeChecker jsonTypeChecker;

    @InjectMocks
    private StringJsonConverter jsonConverter;

    @Before
    public void doBeofre() {
        Mockito.when(jsonValidator.isValidJson(Mockito.anyString())).thenReturn(true);
    }

    @After
    public void doAfter() {
        Mockito.verify(jsonValidator, Mockito.times(1)).isValidJson(Mockito.anyString());
    }

    @Test
    public void shouldReturnJavaClassForJsonObject() throws IOException {
        Mockito.when(jsonTypeChecker.isObject(Mockito.anyString())).thenReturn(true);
        Mockito.when(jsonTypeChecker.isArray(Mockito.anyString())).thenReturn(false);

        List<JsonClassResult> javaClasses = jsonConverter.convertToJava(jsonObject(), JsonToJavaTestConstants.JSON_OBJECT_NAME,"com.package");

        Assert.assertNotNull(javaClasses);
        Assert.assertFalse(javaClasses.isEmpty());
    }

    @Test
    public void shouldReturnClassForObjectsInListForJsonArray() throws IOException {
        Mockito.when(jsonTypeChecker.isObject(Mockito.anyString())).thenReturn(false).thenReturn(true);
        Mockito.when(jsonTypeChecker.isArray(Mockito.anyString())).thenReturn(true).thenReturn(false);
        List<JsonClassResult> json = jsonConverter.convertToJava(jsonArray(), JsonToJavaTestConstants.JSON_OBJECT_NAME,"com.package");

        Assert.assertNotNull(json);
        Assert.assertFalse(json.isEmpty());
    }


    protected String jsonObject() throws IOException {
        return new String(Files.readAllBytes(Paths.get(JsonToJavaTestConstants.JSON_OBJECT_FILE_PATH)));
    }

    protected String jsonArray() throws IOException {
        return new String(Files.readAllBytes(Paths.get(JsonToJavaTestConstants.JSON_ARRAY_FILE_PATH)));
    }
}
