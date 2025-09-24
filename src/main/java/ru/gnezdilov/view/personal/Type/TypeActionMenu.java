package ru.gnezdilov.view.personal.Type;


import org.springframework.stereotype.Component;
import ru.gnezdilov.service.dto.TypeDTO;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.service.personal.TypeService;
import ru.gnezdilov.view.UIUtils;

import java.util.List;

@Component
public class TypeActionMenu {
    private final UIUtils utils;
    private final TypeService typeService;

    public TypeActionMenu(UIUtils utils, TypeService typeService) {
        this.utils = utils;
        this.typeService = typeService;
    }

    public void showAll(UserDTO currentUser) {
        List<TypeDTO> types = typeService
                .getAll(currentUser.getId());
        if (types.isEmpty()) {
            System.out.println("No types transaction found");
        } else {
            types.forEach(System.out::println);
        }
    }

    public void updateTypeTransaction(UserDTO currentUser) {
        int typeId = this.utils.enterId();
        String newName = this.utils.enterNameType(true);
        TypeDTO type = this.typeService.edit(typeId, currentUser.getId(), newName);
        if (type != null) {
            System.out.println("Type updated. New type: " + type.getName());
        } else {
            System.out.println("Update failed");
        }
    }


    public void createTypeTransaction(UserDTO currentUser) {
        String name = this.utils.enterNameType(false);
        TypeDTO typeDTO = typeService
                .create(currentUser.getId(), name);
        System.out.println("Type create: " + typeDTO.getName());
    }

    public void removeTypeTransaction(UserDTO currentUser) {
        int id = this.utils.enterId();
        if (typeService.delete(id, currentUser.getId())) {
            System.out.println("Type: " + id + " - deleted");
        } else {
            System.out.println("Type: " + id + " - not found");
        }
    }
}
