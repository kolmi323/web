package s03.view.PersonalPage;

import s03.view.CustomInterface.Page;
import s03.view.EnterUserData.InputRequest;
import s03.view.UserDataControl.ControlExit;
import s03.service.DTO.ServiceDTO;
import s03.service.DTO.UserDTO;

public class PersonalOfficePage implements Page {
    private final InputRequest inputRequest;
    private final ControlExit controlExit;
    private final UserDTO selectedUser;
    private final AccountActionMenu accountActionMenu;

    public PersonalOfficePage(ServiceDTO service, UserDTO userDTO) {
        this.selectedUser = userDTO;
        this.inputRequest = new InputRequest();
        this.controlExit = new ControlExit();
        this.accountActionMenu = new AccountActionMenu(service, userDTO);
    }

    @Override
    public void startPage() {
        String answer;
        System.out.println("Welcome to personal office " + selectedUser.getName() + "!");
        while (true) {
            answer = this.inputRequest.requestStr(
                    "Please choice action menu:" +
                            "\n1. Account action" +
                            "\n2. Type transaction action" +
                            "\n3. other" +
                            "\nexit. Exit"
            );
            if (answer.equals("1")) {
                accountActionMenu.startActionMenu();
            } else if (answer.equals("2")) {

            } else if (answer.equals("3")) {

            } else if (controlExit.isExitAction(answer)) {
                System.out.println("You exit from person office!");
                return;
            } else {
                System.out.println("Invalid input");
            }
        }
    }
}
