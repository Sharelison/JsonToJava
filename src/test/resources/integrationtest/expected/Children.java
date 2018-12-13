package integrationtest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Children {

    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("name")
    private String name;

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

}