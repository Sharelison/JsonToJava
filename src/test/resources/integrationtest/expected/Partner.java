package integrationtest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Partner {

    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("name")
    private String name;
    @JsonProperty("dogName")
    private String dogName;
    @JsonProperty("age")
    private Integer age;
    @JsonProperty("dog")
    private Dog dog;

    public String getLastName(){
        return this.lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDogName(){
        return this.dogName;
    }

    public void setDogName(String dogName){
        this.dogName = dogName;
    }

    public Integer getAge(){
        return this.age;
    }

    public void setAge(Integer age){
        this.age = age;
    }

    public Dog getDog(){
        return this.dog;
    }

    public void setDog(Dog dog){
        this.dog = dog;
    }

}