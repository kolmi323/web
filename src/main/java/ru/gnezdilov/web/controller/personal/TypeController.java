package ru.gnezdilov.web.controller.personal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public String type(HttpServletRequest request) {
        Integer userId = this.pullUserIdFromSession(request);
        if (userId == null) {
            return "redirect:/start";
        }
        return "personal/type/type_main";
    }

    @GetMapping("/show")
    public String showTypes(HttpServletRequest request,
                            Model model) {
        Integer userId = this.pullUserIdFromSession(request);
        if (userId == null) {
            return "redirect:/start";
        }
        List<TypeDTO> types = typeService.getAll(userId);
        model.addAttribute("types", types);
        return "personal/type/type_show";
    }

    @GetMapping("/add")
    public String addType(Model model) {
        model.addAttribute("form", new TypeAddForm());
        model.addAttribute("message", " ");
        return "personal/type/type_add";
    }

    @PostMapping("/add")
    public String addType(@ModelAttribute("form") @Valid TypeAddForm form,
                          BindingResult result,
                          Model model,
                          HttpServletRequest request) {
        Integer userId = this.pullUserIdFromSession(request);
        if (userId == null) {
            return "redirect:/start";
        }
        if (!result.hasErrors()) {
            try {
                TypeDTO type = typeService.create(userId, form.getName());
                if (type != null) {
                    model.addAttribute("message", "Account " + type.getId() + " - created");
                }
            } catch (Exception e) {
                result.addError(new FieldError("form", "name", e.getMessage()));
            }
        }
        model.addAttribute("form", form);
        return "personal/type/type_add";
    }

    @GetMapping("/update")
    public String updateType(Model model) {
        model.addAttribute("form", new TypeUpdateForm());
        model.addAttribute("message", " ");
        return "personal/type/type_update";
    }

    @PostMapping("/update")
    public String updateType(@ModelAttribute("form") @Valid TypeUpdateForm form,
                             BindingResult result,
                             Model model,
                             HttpServletRequest request) {
        Integer userId = this.pullUserIdFromSession(request);
        if (userId == null) {
            return "redirect:/start";
        }
        if (!result.hasErrors()) {
            try {
                if (typeService.edit(form.getId(), userId, form.getNewName())) {
                    model.addAttribute("message", "Type successfully update");
                } else {
                    model.addAttribute("message", "Update failed");
                }
            } catch (Exception e) {
                result.addError(new FieldError("form", "name", e.getMessage()));
            }
        }
        model.addAttribute("form", form);
        return "personal/type/type_update";
    }

    @GetMapping("/delete")
    public String deleteType(Model model) {
        model.addAttribute("form", new DeleteForm());
        model.addAttribute("message", " ");
        return "personal/type/type_delete";
    }

    @PostMapping("/delete")
    public String deleteType(@ModelAttribute("form") @Valid DeleteForm form,
                             BindingResult result,
                             Model model,
                             HttpServletRequest request) {
        Integer userId = this.pullUserIdFromSession(request);
        if (userId == null) {
            return "redirect:/start";
        }
        if (!result.hasErrors()) {
            try {
                if (typeService.delete(form.getId(), userId)) {
                    model.addAttribute("message", "Type " + form.getId() + " - successfully deleted");
                } else {
                    model.addAttribute("message", "Delete failed");
                }
            } catch (Exception e) {
                result.addError(new FieldError("form", "id", e.getMessage()));
            }
        }
        model.addAttribute("form", form);
        return "personal/type/type_delete";
    }
}
