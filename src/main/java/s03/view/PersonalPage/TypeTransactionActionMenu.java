package s03.view.PersonalPage;

import s03.CustomException.ActionControlException;
import s03.service.DTO.ServiceDTO;
import s03.service.DTO.UserDTO;
import s03.view.AbstractClass.ActionControlPanel;
import s03.view.AbstractClass.ActionMenu;

public class TypeTransactionActionMenu extends ActionMenu<TypeTransactionActionControlPanel> {
    public TypeTransactionActionMenu(ServiceDTO serviceDTO, UserDTO selectedUser) {
        super(serviceDTO, selectedUser);
    }

    @Override
    protected TypeTransactionActionControlPanel createActionControlPanel(ServiceDTO service, UserDTO selectedUser) {
        return new TypeTransactionActionControlPanel(service, selectedUser);
    }

    @Override
    public void startActionMenu() {
        String answer;
        System.out.println("Welcome to type transaction action menu " + selectedUser.getName() + "!");
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
                    actionControlPanel.showListTypeTransaction();
                } else if (answer.equals("2")) {
                    actionControlPanel.update();
                } else if (answer.equals("3")) {
                    actionControlPanel.add();
                } else if (answer.equals("4")) {
                    actionControlPanel.remove();
                } else if (controlExit.isExitAction(answer)) {
                    System.out.println("You exit from type transaction action menu!");
                    return;
                } else {
                    System.out.println("Invalid input");
                }
            } catch (ActionControlException ace) {
                System.out.println(ace.getMessage());
            }
        }
    }
}
