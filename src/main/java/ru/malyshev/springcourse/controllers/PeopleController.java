package ru.malyshev.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.malyshev.springcourse.dao.PersonDAO;


@Controller
@RequestMapping("/people")
public class PeopleController {
    //Иммитатор класса который взаимодействует с базой данных
    private final PersonDAO personDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO){
        this.personDAO = personDAO;
    }

   @GetMapping()
   public String index(Model model){
       //Получить всех людей и передать на представление
       model.addAttribute("people", personDAO.index());
       return "people/index";
   }

   @GetMapping("/{id}")
   public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personDAO.show(id));
       return "people/show";
   }
}