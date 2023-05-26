package ru.malyshev.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.malyshev.springcourse.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    //Образец создания подключения к базе без JDBC template описан в ветке предыдущего урока!
    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Описание метода без JDBC template описан в ветке предыдущего урока!
    public List<Person> index(){
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id){
        //Лекция 27 (12:50) -> Объяснение эого метода и как Он работает.
        //Для чтения из таблицы используем метод query() на jdbcTemplate
        return jdbcTemplate.query("SELECT *  FROM Person WHERE id=?",
                        new Object[]{id},
                        new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person person){
        //Для обновления таблицы используем метод update() на jdbcTemplate
        jdbcTemplate.update("INSERT INTO Person (name, age, email) VALUES (?, ?, ?)",
                person.getName(), person.getAge(), person.getEmail());
    }

    public void update(int id, Person updatedPerson){
        jdbcTemplate.update("UPDATE Person SET name=?, age=?, email=? WHERE id=?",
                updatedPerson.getName(),
                updatedPerson.getAge(),
                updatedPerson.getEmail(),
                id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }
}
