package ru.gnezdilov.view.personal.Type;


import ru.gnezdilov.service.dto.TypeDTO;
import ru.gnezdilov.service.personal.TypeService;
import ru.gnezdilov.view.UIUtils;
import ru.gnezdilov.view.ViewFactory;

import java.util.List;

public class TypeActionMenu {
    private final UIUtils utils;
    private final TypeService typeService;

    public TypeActionMenu(UIUtils utils, TypeService typeService) {
        this.utils = utils;
        this.typeService = typeService;
    }

    public void showAll() {
        List<TypeDTO> types = typeService
                .getAll(ViewFactory.getCurrentUser().getId());
        if (types.isEmpty()) {
            System.out.println("No types transaction found");
        } else {
            types.forEach(System.out::println);
        }
    }

    public void updateTypeTransaction() {
        String typeId = this.utils.enterId();
        if (utils.isExitAction(typeId)) {
            return;
        }
        String newName = this.utils.enterNameType(true);
        if (utils.isExitAction(newName)) {
            return;
        }
        TypeDTO newType = typeService
                .edit(Integer.parseInt(typeId), ViewFactory.getCurrentUser().getId(), newName);
        System.out.println("New name: " + newType.getName());
    }


    public void createTypeTransaction() {
        String name = this.utils.enterNameType(false);
        if (this.utils.isExitAction(name)) {
            return;
        }
        TypeDTO typeDTO = typeService
                .create(ViewFactory.getCurrentUser().getId(), name);
        System.out.println("Type create: " + typeDTO.getName());
    }

    public void removeTypeTransaction() {
        String id = this.utils.enterId();
        if (this.utils.isExitAction(id)) {
            return;
        }
        if (typeService.delete(Integer.parseInt(id), ViewFactory.getCurrentUser().getId())) {
            System.out.println("Type: " + id + " - deleted");
        } else {
            System.out.println("Type: " + id + " - not found");
        }
    }
}
