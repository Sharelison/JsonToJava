package io.github.sharelison.jsontojava.converter.factory;

import io.github.sharelison.jsontojava.converter.FileJsonConverter;
import io.github.sharelison.jsontojava.converter.JsonConverter;
import io.github.sharelison.jsontojava.converter.StringJsonConverter;
import org.junit.Assert;
import org.junit.Test;

public class JsonConverterFactoryTest {

    JsonConverterFactory factory = new JsonConverterFactory(null, null, null);

    @Test
    public void shouldReturnFileJsonConverterForParamHasExtension() {
        String param = "sdf/sdfds/sdf.json";
        JsonConverter converter = factory.createJsonConverter(param);

        Assert.assertTrue(converter instanceof FileJsonConverter);
    }

    @Test
    public void shouldReturnStringJsonConverterForParamHasNoExtension() {
        String param = "{}";
        JsonConverter converter = factory.createJsonConverter(param);

        Assert.assertTrue(converter instanceof StringJsonConverter);
    }
}
