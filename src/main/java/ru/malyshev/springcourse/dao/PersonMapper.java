package ru.malyshev.springcourse.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.malyshev.springcourse.models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

//параметризуем метод под объект который хотим параметризовать.
public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet resultSet, int i) throws SQLException {
        Person person = new Person();

        person.setId(resultSet.getInt("id"));
        person.setAge(resultSet.getInt("age"));
        person.setName(resultSet.getString("name"));
        person.setEmail(resultSet.getString("email"));

        return person;
    }
}
