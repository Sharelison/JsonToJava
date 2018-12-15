package io.github.sharelison.jsontojava.converter.builder;

import io.github.sharelison.jsontojava.exception.JsonToJavaException;
import org.junit.Assert;
import org.junit.Test;

public class JavaClassBuilderTest {

    private String newLine = System.lineSeparator();

    @Test
    public void hasPropertyShouldReturnTrueForAddedProperty() {
        JavaClassBuilder javaClassBuilder = new JavaClassBuilder("Bd", "com.package");
        javaClassBuilder.addProperty("propName", "PropName");
        Assert.assertTrue(javaClassBuilder.hasProperty("propName"));
    }

    @Test
    public void addPropertyShouldAddPropertyWithGettersAndSetters() {
        JavaClassBuilder javaClassBuilder = new JavaClassBuilder("B", "sdf");
        javaClassBuilder.addProperty("propName", "PropName");

        String expectedClass = "package sdf;"+newLine +
                newLine +
                "import com.fasterxml.jackson.annotation.JsonProperty;"+newLine +
                newLine +
                "public class B {"+newLine +
                newLine +
                "    @JsonProperty(\"propName\")"+newLine +
                "    private PropName propName;"+newLine +
                newLine +
                "    public PropName getPropName(){"+newLine +
                "        return this.propName;"+newLine +
                "    }"+newLine +
                newLine +
                "    public void setPropName(PropName propName){"+newLine +
                "        this.propName = propName;"+newLine +
                "    }"+newLine +
                newLine +
                "}";

        Assert.assertEquals(expectedClass, javaClassBuilder.build());
    }

    @Test
    public void importStatementShouldNotImportDuplicateImports() {
       String importStatement = "com.my.import.statement";
       JavaClassBuilder javaClassBuilder = new JavaClassBuilder("B", "b.b");

       javaClassBuilder.addImportStatement(importStatement);
       javaClassBuilder.addImportStatement(importStatement);

       String javaClass = javaClassBuilder.build();

       Assert.assertTrue(javaClass.contains(importStatement));
       Assert.assertEquals(javaClass.indexOf(importStatement), javaClass.lastIndexOf(importStatement));
    }

    @Test
    public void declareClassShouldDeclareClassForGivenNameAndPackage() {
       JavaClassBuilder javaClassBuilder = new JavaClassBuilder("A", "a.b");
       Assert.assertEquals("package a.b;"+newLine+newLine+"import com.fasterxml.jackson.annotation.JsonProperty;"+newLine+newLine+ "public class A {"+newLine+newLine+newLine+ "}", javaClassBuilder.build());
    }

    @Test
    public void declareClassShouldRemoveUnwantedCharactersForClassNames() {
        String[] exampleUnwantedCharactersForClassName = {"!","#", "^", "%", "-", "+", "=", ""};

        for(int i = 0; i < exampleUnwantedCharactersForClassName.length; i++){
            String className = "My"+exampleUnwantedCharactersForClassName[i]+"Class";
            JavaClassBuilder javaClassBuilder = new JavaClassBuilder(className, "com.package");
            Assert.assertEquals("MyClass", javaClassBuilder.getClassName());
        }
    }

    @Test(expected = JsonToJavaException.class)
    public void declareClassShouldThrowExceptionForNoValidCharactersInClassName() {
        String invalidClassName = "!@%^&*(";

        JavaClassBuilder javaClassBuilder = new JavaClassBuilder(invalidClassName, "sdf");
    }

    @Test(expected = JsonToJavaException.class)
    public void constructorShouldThrowExceptionForEmptyPackageName() {
        new JavaClassBuilder("", "asdf");
    }

    @Test(expected = JsonToJavaException.class)
    public void constructorShouldThrowExceptionForEmptyClassName() {
        new JavaClassBuilder("fsdhg", "");
    }

    @Test
    public void shouldNotAddAnnotationsForWithAnnotationsIsDisabled() {
        JavaClassBuilder javaClassBuilder = new JavaClassBuilder("B", "sdf", false);
        javaClassBuilder.addProperty("propName", "PropName");

        String expectedClass = "package sdf;"+newLine +
                newLine +
                "public class B {"+newLine +
                newLine +
                "    private PropName propName;"+newLine +
                newLine +
                "    public PropName getPropName(){"+newLine +
                "        return this.propName;"+newLine +
                "    }"+newLine +
                newLine +
                "    public void setPropName(PropName propName){"+newLine +
                "        this.propName = propName;"+newLine +
                "    }"+newLine +
                newLine +
                "}";

        Assert.assertEquals(expectedClass, javaClassBuilder.build());    }
}
