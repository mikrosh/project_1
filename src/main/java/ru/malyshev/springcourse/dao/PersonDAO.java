package ru.malyshev.springcourse.dao;

import org.springframework.stereotype.Component;
import ru.malyshev.springcourse.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;

    //Переменные для подключения к базе данных, вообще должны быть в properties,
    // помещать их в статические поля плохо плохо плохо, никогда не нужно так делать!!!
    private static final String URL = "jdbc:postgresql://localhost:5432/first_db";
    private static final String userName = "postgres";
    private static final String password = "admin";

    //Объект подключения к ДБ
    private static Connection connection;

    //Инициализиция объекта подключения
    static {
        try {
            //Подгружаем класс с jdbc драйверов (в последних версиях происходит автомитаически)
            Class.forName("org.postgresql.Driver"); //Эта хрень называется рефлексия
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Person> index(){
        List<Person> people = new ArrayList<>();

        //объект, который содержит в себе sql запрос к базе данных
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM Person";
            //Выполняем sql запрос (statement.executeQuery(SQL))
            // ResultSet это то, что возвращает метод выполняющий запрос,
            // все вернувшиеся строки из таблицы, теперь лежат в нем
            ResultSet resultSet = statement.executeQuery(SQL); //Этот метод получает данные

            //Каждую строку их БД теперь надо превести в java-объект
            //Идем по резалт сет сдвигаемся на каждом шаге на строку вперед(next)
            while (resultSet.next()){
                //Создаем пустышку Персон в нее будем поочереди класть объекты которые у нас в табличке лежат
                Person person = new Person();
                person.setId(resultSet.getInt("id")); //Значение колонки для строки на которой мы находимся (цикл же)
                person.setName(resultSet.getString("name"));
                person.setAge(resultSet.getInt("age"));
                person.setEmail(resultSet.getString("email"));

                people.add(person);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return people;
    }

    public Person show(int id){
        Person person = null;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM Person WHERE id=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            person = new Person();
            person.setId(resultSet.getInt("id"));
            person.setAge(resultSet.getInt("age"));
            person.setName(resultSet.getString("name"));
            person.setEmail(resultSet.getString("email"));

        } catch (SQLException e){
            e.printStackTrace();
        }

        return person;
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
