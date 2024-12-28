package web.view.PersonalOffice;


import web.service.DTO.TypeTransactionDTO;
import web.service.ServiceFactory;
import web.view.UIUtils;
import web.view.ViewFactory;

import java.util.List;

public class TypeTransactionActionMenu {
    private final UIUtils utils;

    public TypeTransactionActionMenu() {
        this.utils = new UIUtils();
    }

    public void showListTypeTransaction() {
        List<TypeTransactionDTO> types = ServiceFactory.getTypeTransactionAction()
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
        TypeTransactionDTO newType = ServiceFactory.getTypeTransactionAction()
                .edit(ViewFactory.getCurrentUser().getId(), oldName, newName);
        System.out.println("Old name: " + oldName + " New name: " + newType.getName());
    }


    public void createTypeTransaction() {
        String name = this.utils.enterNameType(false);
        if (this.utils.isExitAction(name)) {
            return;
        }
        TypeTransactionDTO typeTransactionDTO = ServiceFactory.getTypeTransactionAction()
                .create(ViewFactory.getCurrentUser().getId(), name);
        System.out.println("Type create: " + typeTransactionDTO.getName());
    }

    public void removeTypeTransaction() {
        String name = this.utils.enterNameType(false);
        if (this.utils.isExitAction(name)) {
            return;
        }
        if (ServiceFactory.getTypeTransactionAction().delete(ViewFactory.getCurrentUser().getId(), name)) {
            System.out.println("Type: " + name + " - deleted");
        } else {
            System.out.println("Type: " + name + " - not found");
        }
    }
}
