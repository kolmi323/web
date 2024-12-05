package s03.view.PersonalPage;

import s03.CustomException.ActionControlException;
import s03.service.Service;
import s03.service.UserDTO;
import s03.view.CustomInterface.ActionMenu;
import s03.view.EnterUserData.InputRequest;
import s03.view.UserDataControl.ControlExit;

public class AccountActionMenu implements ActionMenu {
    private final InputRequest inputRequest;
    private final ControlExit controlExit;
    private final AccountActionControlPanel accountActionControlPanel;
    private final UserDTO selectedUser;

    public AccountActionMenu(Service service, UserDTO selectedUser) {
        this.selectedUser = selectedUser;
        this.accountActionControlPanel = new AccountActionControlPanel(selectedUser, service);
        this.inputRequest = new InputRequest();
        this.controlExit = new ControlExit();
    }

    @Override
    public void startActionMenu() {
        String answer;
        System.out.println("Welcome to account action menu " + selectedUser.getName() + "!");
        while (true) {
            answer = this.inputRequest.requestStr(
                    "Action menu:" +
                            "\n1. Display a list of accounts" +
                            "\n2. Create account" +
                            "\n3. Delete account" +
                            "\nexit. Exit"
            );
            try {
                if (answer.equals("1")) {
                    accountActionControlPanel.showListAccount();
                } else if (answer.equals("2")) {
                    accountActionControlPanel.add();
                } else if (answer.equals("3")) {
                    accountActionControlPanel.remove();
                } else if (controlExit.isExitAction(answer)) {
                    System.out.println("You exit from account action menu!");
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
