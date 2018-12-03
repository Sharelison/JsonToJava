package integrationtest.expected;

public class Partner{

    private String lastName;
    private String name;
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

    public Dog getDog(){
        return this.dog;
    }

    public void setDog(Dog dog){
        this.dog = dog;
    }

}