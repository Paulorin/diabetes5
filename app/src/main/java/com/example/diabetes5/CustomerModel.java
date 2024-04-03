package com.example.diabetes5;

public class CustomerModel {
    private int id;
    private String name;
    private int age;
    // constructors

    /**
     * Вероятно необходимо создавать конструкторы под различное число параметров.
     * @param id
     * @param name
     * @param age
     */
    public CustomerModel(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public CustomerModel() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    //toString
    @Override
    public String toString() {
        return "CustomerModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
