package ru.gnezdilov.web.controller.personal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.gnezdilov.service.dto.TypeDTO;
import ru.gnezdilov.service.personal.TypeService;
import ru.gnezdilov.web.WebController;
import ru.gnezdilov.web.form.DeleteForm;
import ru.gnezdilov.web.form.type.TypeAddForm;
import ru.gnezdilov.web.form.type.TypeUpdateForm;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("type")
public class TypeController extends WebController {
    private final TypeService typeService;

    @GetMapping()
    public String type() {
        return "personal/type/main";
    }

    @GetMapping("/show")
    public String showTypes(Model model) {
        Integer userId = this.currentUser().getId();
        List<TypeDTO> types = typeService.getAll(userId);
        model.addAttribute("types", types);
        return "personal/type/show";
    }

    @GetMapping("/add")
    public String addType(Model model) {
        model.addAttribute("form", new TypeAddForm());
        return "personal/type/add";
    }

    @PostMapping("/add")
    public String addType(@ModelAttribute("form") @Valid TypeAddForm form,
                          BindingResult result,
                          Model model,
                          RedirectAttributes redirectAttributes) {
        Integer userId = this.currentUser().getId();
        if (!result.hasErrors()) {
            TypeDTO type = typeService.create(userId, form.getName());
            return this.redirectMessage("Account " + type.getId() + " - created", redirectAttributes);
        }
        model.addAttribute("form", form);
        return "personal/type/add";
    }

    @GetMapping("/update")
    public String updateType(Model model) {
        model.addAttribute("form", new TypeUpdateForm());
        return "personal/type/update";
    }

    @PostMapping("/update")
    public String updateType(@ModelAttribute("form") @Valid TypeUpdateForm form,
                             BindingResult result,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        Integer userId = this.currentUser().getId();
        if (!result.hasErrors()) {
            TypeDTO type = typeService.edit(form.getId(), userId, form.getNewName());
            return this.redirectMessage("Account " + type.getId() + " - modified. New name: " + type.getName(), redirectAttributes);
        }
        model.addAttribute("form", form);
        return "personal/type/update";
    }

    @GetMapping("/delete")
    public String deleteType(Model model) {
        model.addAttribute("form", new DeleteForm());
        return "personal/type/delete";
    }

    @PostMapping("/delete")
    public String deleteType(@ModelAttribute("form") @Valid DeleteForm form,
                             BindingResult result,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        Integer userId = this.currentUser().getId();
        if (!result.hasErrors()) {
            if (typeService.delete(form.getId(), userId)) {
                return this.redirectMessage("Type " + form.getId() + " - successfully deleted", redirectAttributes);
            } else {
                return this.redirectMessage("Delete failed", redirectAttributes);
            }
        }
        model.addAttribute("form", form);
        return "personal/type/delete";
    }
}
