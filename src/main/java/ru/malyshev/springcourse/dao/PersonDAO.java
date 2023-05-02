package ru.malyshev.springcourse.dao;

import org.springframework.stereotype.Component;
import ru.malyshev.springcourse.models.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;
    private List<Person> people;

    {
        people = new ArrayList();

        people.add(new Person(++PEOPLE_COUNT, "Tom", 34, "tom@mail.ru"));
        people.add(new Person(++PEOPLE_COUNT, "Sam", 30, "sam@mail.ru"));
        people.add(new Person(++PEOPLE_COUNT, "Mike", 23, "mike@mail.ru"));
        people.add(new Person(++PEOPLE_COUNT, "Den", 27,"den@mail.ru"));
    }

    public List<Person> index(){
        return people;
    }

    public Person show(int id){
        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }

    public void save(Person person){
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }

    public void update(int id, Person updatedPerson){
        Person personToBeUpdated = show(id);
        personToBeUpdated.setName(updatedPerson.getName());
        personToBeUpdated.setAge(updatedPerson.getAge());
        personToBeUpdated.setEmail(updatedPerson.getEmail());

    }

    public void delete(int id){
        people.removeIf(p -> p.getId() == id);
    }
}
