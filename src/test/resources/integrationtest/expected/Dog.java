package integrationtest.expected;

public class Dog{

    private Partner1 partner;
    private String name;
    private Integer age;

    public Partner1 getPartner(){
        return this.partner;
    }

    public void setPartner(Partner1 partner){
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