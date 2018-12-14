package io.github.sharelison.jsontojava.validator;

import org.junit.Assert;
import org.junit.Test;

public class InputJsonValidatorTest {

    private JsonValidator inputJsonValidator = new InputJsonValidator();

    @Test
    public void shouldReturnTrueForValidJsonObject() {
        String validJsonObject = "{\n" +
                "  \"field1\": {\n" +
                "    \"fieldoffield1\" : \"\"\n" +
                "  },\n" +
                "  \"field2\": [\"sdf, sdf\"],\n" +
                "  \"field3\": \"sdfsdf\",\n" +
                "  \"field4\": true,\n" +
                "  \"field5\": 24\n" +
                "}";

        Assert.assertTrue(inputJsonValidator.isValidJson(validJsonObject));
    }

    @Test
    public void shouldReturnTrueForValidJsonArray() {
        String validJsonArray = "[\n" +
                "  {\n" +
                "  \"field4\": true,\n" +
                "  \"field5\": 24\n" +
                "  },\n" +
                "  {\n" +
                "  \"field1\": {\n" +
                "    \"fieldoffield1\" : \"\"\n" +
                "  },\n" +
                "  \"field2\": [\"sdf, sdf\"]\n" +
                "  }\n" +
                "]";

        Assert.assertTrue(inputJsonValidator.isValidJson(validJsonArray));
    }

    @Test
    public void shouldReturnFalseForNotValidJsonObject() {
        String inValidJsonObject = "{\n" +
                "  \"field1\": {\n" +
                "    \"fieldoffield1\" : \"\"\n" +
                "  },\n" +
                "  \"field2\": [\"sdf, sdf\"],\n" +
                "  \"field3\": \"sdfsdf\",\n" +
                "  \"field4\": --true,\n" +
                "  \"field5\": 24\n" +
                "";

        Assert.assertFalse(inputJsonValidator.isValidJson(inValidJsonObject));
    }

    @Test
    public void shouldReturnFalseForNotValidJsonArray() {
        String inValidJsonArray = "[\n" +
                "  {\n" +
                "  \"field4\": true,\n" +
                "  \"field5\" 24\n" +
                "  },\n" +
                "  {\n" +
                "  \"field1\": {\n" +
                "    \"fieldoffield1\" : \"\"\n" +
                "  },\n" +
                "  \"field2\": [\"sdf, sdf\"]\n" +
                "  }\n" +
                "]";

        Assert.assertFalse(inputJsonValidator.isValidJson(inValidJsonArray));
    }

}
