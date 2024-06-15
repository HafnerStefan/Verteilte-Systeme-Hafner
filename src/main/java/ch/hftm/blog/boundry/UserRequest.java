package ch.hftm.blog.boundry;

public class UserRequest {

    private String name;
    private int age;

    // Standardkonstruktor
    public UserRequest() {
    }

    // Konstruktor mit Parametern
    public UserRequest(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getter und Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
