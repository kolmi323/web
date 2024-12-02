package s03.view.PersonalPage;

import s03.CustomException.ActionControlException;
import s03.service.PersonOfficeActionService;
import s03.view.EnterUserData.InputRequest;
import s03.view.UserDataControl.ControlExit;
import s03.service.Service;
import s03.service.UserDTO;

public class PersonalOffice {
    private final Service service;
    private final InputRequest inputRequest;
    private final ControlExit controlExit;
    private final UserDTO selectedUser;
    private final ActionControlPanel actionControlPanel;

    public PersonalOffice(Service service, UserDTO userDTO) {
        this.service = service;
        this.selectedUser = userDTO;
        this.inputRequest = new InputRequest();
        this.controlExit = new ControlExit();
        this.actionControlPanel = new ActionControlPanel(userDTO, service);
    }

    public void startPersonalOffice() {
        String answer;
        System.out.println("Welcome to personal office " + selectedUser.getName() + "!");
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
                    actionControlPanel.showListAccount();
                } else if (answer.equals("2")) {
                    actionControlPanel.addAccount();
                } else if (answer.equals("3")) {
                    actionControlPanel.deleteAccount();
                } else if (controlExit.isExitAction(answer)) {
                    System.out.println("You exit from person office!");
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
