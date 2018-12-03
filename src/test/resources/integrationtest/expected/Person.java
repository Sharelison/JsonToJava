package integrationtest.expected;

import java.util.List;

public class Person{

    private String lastName;
    private Partner partner;
    private List<Children> children;
    private String name;

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