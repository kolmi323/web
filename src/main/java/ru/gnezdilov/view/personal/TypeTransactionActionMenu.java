package ru.gnezdilov.view.personal;


import ru.gnezdilov.service.dto.TypeTransactionDTO;
import ru.gnezdilov.service.personal.TypeTransactionService;
import ru.gnezdilov.view.UIUtils;
import ru.gnezdilov.view.ViewFactory;

import java.util.List;

public class TypeTransactionActionMenu {
    private final UIUtils utils;
    private final TypeTransactionService typeTransactionService;

    public TypeTransactionActionMenu(UIUtils utils, TypeTransactionService typeTransactionService) {
        this.utils = utils;
        this.typeTransactionService = typeTransactionService;
    }

    public void showAll() {
        List<TypeTransactionDTO> types = typeTransactionService
                .getAll(ViewFactory.getCurrentUser().getId());
        if (types.isEmpty()) {
            System.out.println("No types transaction found");
        } else {
            types.forEach(System.out::println);
        }
    }

    public void updateTypeTransaction() {
        String oldName = this.utils.enterNameType(false);
        if (utils.isExitAction(oldName)) {
            return;
        }
        String newName = this.utils.enterNameType(true);
        if (utils.isExitAction(newName)) {
            return;
        }
        TypeTransactionDTO newType = typeTransactionService
                .edit(ViewFactory.getCurrentUser().getId(), oldName, newName);
        System.out.println("Old name: " + oldName + " New name: " + newType.getName());
    }


    public void createTypeTransaction() {
        String name = this.utils.enterNameType(false);
        if (this.utils.isExitAction(name)) {
            return;
        }
        TypeTransactionDTO typeTransactionDTO = typeTransactionService
                .create(ViewFactory.getCurrentUser().getId(), name);
        System.out.println("Type create: " + typeTransactionDTO.getName());
    }

    public void removeTypeTransaction() {
        String id = this.utils.enterId();
        if (this.utils.isExitAction(id)) {
            return;
        }
        if (typeTransactionService.delete(Integer.parseInt(id), ViewFactory.getCurrentUser().getId())) {
            System.out.println("Type: " + id + " - deleted");
        } else {
            System.out.println("Type: " + id + " - not found");
        }
    }
}
