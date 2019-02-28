package integrationtest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Pp {

    @JsonProperty("name")
    private String name;
    @JsonProperty("age")
    private Integer age;

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Integer getAge(){
        return this.age;
    }

    public void setAge(Integer age){
        this.age = age;
    }

}