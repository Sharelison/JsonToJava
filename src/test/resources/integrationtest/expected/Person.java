package integrationtest;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Person {

    @JsonProperty("pp")
    private List<List<Pp>> pp;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("partner")
    private Partner partner;
    @JsonProperty("children")
    private List<Children> children;
    @JsonProperty("name")
    private String name;

    public List<List<Pp>> getPp(){
        return this.pp;
    }

    public void setPp(List<List<Pp>> pp){
        this.pp = pp;
    }

    public String getLastName(){
        return this.lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public Partner getPartner(){
        return this.partner;
    }

    public void setPartner(Partner partner){
        this.partner = partner;
    }

    public List<Children> getChildren(){
        return this.children;
    }

    public void setChildren(List<Children> children){
        this.children = children;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

}