package ru.malyshev.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
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
        return jdbcTemplate.query("SELECT * FROM Person", new PersonMapper());
    }

    public Person show(int id){
        //Лекция 27 (12:50) -> Объяснение эого метода и как Он работает.
        return jdbcTemplate.query("SELECT *  FROM Person WHERE id=?",
                        new Object[]{id},
                        new PersonMapper())
                .stream().findAny().orElse(null);
    }

    public void save(Person person){
        try{
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO Person VALUES(1, ?, ?, ?)");

            preparedStatement.setString(1, person.getName());
            preparedStatement.setInt(2, person.getAge());
            preparedStatement.setString(3, person.getEmail());

            preparedStatement.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void update(int id, Person updatedPerson){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE Person SET name=?, age=?, email=? WHERE id=?");

            preparedStatement.setString(1, updatedPerson.getName());
            preparedStatement.setInt(2, updatedPerson.getAge());
            preparedStatement.setString(3, updatedPerson.getEmail());
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void delete(int id){
        try{
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM Person WHERE id=?");

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
