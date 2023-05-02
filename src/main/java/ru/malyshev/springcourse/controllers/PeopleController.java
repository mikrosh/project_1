package ru.malyshev.springcourse.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.malyshev.springcourse.dao.PersonDAO;
import ru.malyshev.springcourse.models.Person;


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

   @GetMapping("/new")
   public String newPerson(Model model){
        model.addAttribute("person", new Person());
        return "people/new";
   }

   @PostMapping()
   //@Valid включает проверку данного объекта по аннотациям указанным в его классе @ModelAttribute создает новый объект
   // person значения из формы создания внедряет в этот объект, а затем помещает этот объект в модель,
   // через которую мы его уже получаем в представлении. BindingResult вставляется сразу после объекта. который
   // валидируем, если объект содержит ошибки валидации, то он внедряется в BindingResult,
   // который тут же проверяется на ошибки, и если они есть, возвращаем представление в данном случае стартовой страницы,
   // а на стартовой странице уже с помощью Thymeleaf показываем ошибки из этого объекта
   public String create(@ModelAttribute("person") @Valid Person person,
                        BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "people/new";

       personDAO.save(person);
       return "redirect:/people";
   }

   @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
   }

   @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult, @PathVariable("id") int id){
        if(bindingResult.hasErrors())
            return "people/edit";

        personDAO.update(id, person);
        return "redirect:/people";
   }

   @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        personDAO.delete(id);
        return "redirect:/people";
   }


}
