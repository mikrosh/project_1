package ru.malyshev.springcourse.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Person {
    private int id;

    @NotEmpty(message = "Name should not be empty") //устанавливаем что имя не может быть пустым
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters") //Устанавливаем мин и макс число символов в имени
    private String name;

    @Min(value = 0, message = "Age should be greater than 0") //Установили мин. значние возраста
    private int age;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "You must provide valid email") //Валидатор email (не надо регулярки писать)
    private String email;

    public Person(int id, String name, int age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }
    public Person(){}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
