package ru.gnezdilov.view.personal.Type;

import org.springframework.stereotype.Component;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.view.InputRequest;
import ru.gnezdilov.view.UIUtils;
import ru.gnezdilov.dao.exception.*;

import javax.persistence.EntityNotFoundException;

@Component
public class TypeMenu {
    private final InputRequest inputRequest;
    private final UIUtils utils;
    private final TypeActionMenu typeActionMenu;

    public TypeMenu(InputRequest inputRequest, UIUtils utils,
                    TypeActionMenu typeActionMenu) {
        this.inputRequest = inputRequest;
        this.utils = utils;
        this.typeActionMenu = typeActionMenu;
    }

    public void start(UserDTO currentUser) {
        String answer;
        System.out.println("Welcome to type transaction action menu " + currentUser.getName() + "!");
        while (true) {
            answer = this.inputRequest.requestStr(
                    "Action menu:" +
                            "\n1. Display a list of name type" +
                            "\n2. Edit name type" +
                            "\n3. Create type" +
                            "\n4. Delete type" +
                            "\nexit. Exit"
            );
            try {
                if (answer.equals("1")) {
                    typeActionMenu.showAll(currentUser);
                } else if (answer.equals("2")) {
                    typeActionMenu.updateTypeTransaction(currentUser);
                } else if (answer.equals("3")) {
                    typeActionMenu.createTypeTransaction(currentUser);
                } else if (answer.equals("4")) {
                    typeActionMenu.removeTypeTransaction(currentUser);
                } else if (utils.isExitAction(answer)) {
                    System.out.println("You exit from type transaction action menu!");
                    return;
                } else {
                    System.out.println("Invalid input");
                }
            } catch (EntityNotFoundException | NotFoundException | DAOException | NullPointerException | ExitException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
