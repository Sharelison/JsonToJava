package integrationtest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Dog {

    @JsonProperty("partner")
    private Partner partner;
    @JsonProperty("name")
    private String name;
    @JsonProperty("age")
    private Integer age;

    public Partner getPartner(){
        return this.partner;
    }

    public void setPartner(Partner partner){
        this.partner = partner;
    }

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