package s03.view.PersonalPage;

import s03.CustomException.ActionControlException;
import s03.service.PersonOfficeActionService;
import s03.service.Service;
import s03.service.UserDTO;
import s03.view.EnterUserData.UserDataReceiver;
import s03.view.UserDataControl.ControlExit;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ActionControlPanel {
    private final PersonOfficeActionService service;
    private final UserDataReceiver userDataReceiver;
    private final ControlExit controlExit;
    private final UserDTO currentUser;

    public ActionControlPanel(UserDTO currentUser, Service service) {
        this.currentUser = currentUser;
        this.service = new PersonOfficeActionService(service, currentUser);
        this.userDataReceiver = new UserDataReceiver(service);
        this.controlExit = new ControlExit();
    }

    public void showListAccount() {
        this.service.returnListAccount().stream().forEach(System.out::println);
    }

    public void addAccount() throws ActionControlException {
        String name = this.userDataReceiver.enterNameAccount(this.currentUser.getId(), true);
        if (this.controlExit.isExitAction(name)) {
            return;
        }
        String answer = this.userDataReceiver.enterBalanceAccount();
        if (this.controlExit.isExitAction(answer)) {
            return;
        }
        BigDecimal balance = new BigDecimal(answer).setScale(2, RoundingMode.HALF_UP);
        if (this.service.createAccount(name, balance)) {
            System.out.println("Account: " + name + " - created");
        } else {
            throw new ActionControlException();
        }
    }

    public void deleteAccount() {
        String name = this.userDataReceiver.enterNameAccount(this.currentUser.getId(), false);
        if (this.controlExit.isExitAction(name)) {
            return;
        }
        if (this.service.deleteAccount(currentUser.getId(), name)) {
            System.out.println("Account: " + name + " - deleted");
        } else {
            System.out.println("Account: " + name + " - error");
        }
    }
}
