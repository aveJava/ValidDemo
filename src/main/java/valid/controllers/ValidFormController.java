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
import java.util.List;

@Controller
public class ValidFormController {

    @GetMapping("/")
    public String get(Model model) {
        System.out.println("Начал работать get-контроллер");
        // кладем редактируемый объект как EditableObject (если он уже есть, то не кладем)
        if (!model.containsAttribute("EditableObject")) {
            model.addAttribute("EditableObject", new MyObject());
            System.out.println("get-контроллер положил новый EditableObject в модель");
        } else System.out.println("get-контроллер не стал класть новый EditableObject в модель, т.к. он уже в ней есть");
        System.out.println("get-контроллер выдал форму редактирования");
        // показываем пользователю форму редактирования
        return "form";
    }

    @PostMapping("/")
    public String post(@Valid @ModelAttribute("EditableObject") MyObject obj, BindingResult binding, RedirectAttributes redirAttr) {
        System.out.println("Начал работать post-контроллер");

//        if (binding.hasErrors()) {
//            return "form";
//        }

        if (binding.hasErrors()) {
            System.out.println("Обнаружены ошибки");

            redirAttr.addFlashAttribute("errors", binding.getAllErrors());

            // распечатка ошибки
            ObjectError error = binding.getAllErrors().get(0);
            System.out.println("*+*+*+*+*+*+*+*+*+*+*+");
            System.out.println("error.getObjectName() - " + error.getObjectName());
            System.out.println("error.getDefaultMessage() - " + error.getDefaultMessage());
            System.out.println("error.getArguments() : \n ---------------------");
            for (Object err : error.getArguments()) {
                System.out.println(err);
            }
            System.out.println("---------------------");
            System.out.println("error.toString() : \n" + error.toString());
            System.out.println("*+*+*+*+*+*+*+*+*+*+*+");

            redirAttr.addFlashAttribute("EditableObject", obj);
            System.out.println("post-контроллер настроил RedirectAttributes и перенаправил на get-контроллер");
            return "redirect:/";
        }
        redirAttr.addFlashAttribute("EditableObject", obj);
        System.out.println("Нет никаких ошибок. Перенаправление в get-контроллер");

        return "redirect:/";
    }

}
