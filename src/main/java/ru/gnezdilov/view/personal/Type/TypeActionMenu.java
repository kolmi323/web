package ru.gnezdilov.view.personal.Type;


import org.springframework.stereotype.Component;
import ru.gnezdilov.service.dto.TypeDTO;
import ru.gnezdilov.service.personal.TypeService;
import ru.gnezdilov.view.CurrentUser;
import ru.gnezdilov.view.UIUtils;

import java.util.List;

@Component
public class TypeActionMenu {
    private final UIUtils utils;
    private final TypeService typeService;
    private final CurrentUser currentUser;

    public TypeActionMenu(UIUtils utils, TypeService typeService, CurrentUser currentUser) {
        this.utils = utils;
        this.typeService = typeService;
        this.currentUser = currentUser;
    }

    public void showAll() {
        List<TypeDTO> types = typeService
                .getAll(currentUser.getCurrentUser().getId());
        if (types.isEmpty()) {
            System.out.println("No types transaction found");
        } else {
            types.forEach(System.out::println);
        }
    }

    public void updateTypeTransaction() {
        int typeId = this.utils.enterId();
        String newName = this.utils.enterNameType(true);
        TypeDTO newType = typeService.edit(typeId, currentUser.getCurrentUser().getId(), newName);
        System.out.println("New name: " + newType.getName());
    }


    public void createTypeTransaction() {
        String name = this.utils.enterNameType(false);
        TypeDTO typeDTO = typeService
                .create(currentUser.getCurrentUser().getId(), name);
        System.out.println("Type create: " + typeDTO.getName());
    }

    public void removeTypeTransaction() {
        int id = this.utils.enterId();
        if (typeService.delete(id, currentUser.getCurrentUser().getId())) {
            System.out.println("Type: " + id + " - deleted");
        } else {
            System.out.println("Type: " + id + " - not found");
        }
    }
}
