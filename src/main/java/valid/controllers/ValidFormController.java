package valid.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import valid.models.MyObject;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ValidFormController {

    @GetMapping("/")
    public String get(Model model) {
        // кладем редактируемый объект как EditableObject (если он уже есть, то не кладем)
        if (!model.containsAttribute("EditableObject")) {
            model.addAttribute("EditableObject", new MyObject());
        }
        // показываем пользователю форму редактирования
        return "form";
    }

    @PostMapping("/")
    public String post(@Valid @ModelAttribute("EditableObject") MyObject obj,
                       BindingResult binding, RedirectAttributes redirAttr) {

        if (binding.hasErrors()) {
            // получение списка сообщений об ошибках из BindingResult
            List<String> errMessages = new ArrayList<>();
            for (ObjectError err : binding.getAllErrors()) {
                errMessages.add(err.getDefaultMessage());
            }
            // передача списка сообщений об ошибках в модель контроллера, который будет вызван по редиректу, как атрибут errors
            redirAttr.addFlashAttribute("errors", errMessages);
            // передача объекта с ошибочно заполненными полями в модель контроллера, который будет вызван по редиректу, как атрибут EditableObject
            redirAttr.addFlashAttribute("EditableObject", obj);

            // распечатка ошибки
            ObjectError error = binding.getAllErrors().get(0);
            error.getObjectName();      // имя объекта (к полю которого относится данная ошибка), под которым он был передан на front
            error.getDefaultMessage();  // сообщение для пользователя
            error.getArguments();       // возвращает непонятно что
            error.toString();           // возвращает всю информацию, которая содержится в данном объекте

            return "redirect:/";
        }

        return "redirect:/";
    }

}
