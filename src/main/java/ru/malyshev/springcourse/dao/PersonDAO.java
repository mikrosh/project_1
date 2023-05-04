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
//        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
        return null;
    }

    public void save(Person person){
//        person.setId(++PEOPLE_COUNT);
//        people.add(person);
        try{
            Statement statement = connection.createStatement();
            String SQL = "INSERT INTO Person VALUES(" + 1 + ",'" + person.getName() +
                    "'," + person.getAge() + ",'" + person.getEmail() + "')";

            //Выше сконструирована строка: INSERT INTO Person VALUES(1, '<Name>', '<age>', '<email>')
            statement.executeUpdate(SQL); //Этот метод обновляет данные
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void update(int id, Person updatedPerson){
//        Person personToBeUpdated = show(id);
//        personToBeUpdated.setName(updatedPerson.getName());
//        personToBeUpdated.setAge(updatedPerson.getAge());
//        personToBeUpdated.setEmail(updatedPerson.getEmail());

    }

    public void delete(int id){
//        people.removeIf(p -> p.getId() == id);
    }
}
