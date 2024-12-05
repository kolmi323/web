package s03.view.PersonalPage;

import s03.CustomException.ActionControlException;
import s03.dao.AccountModel;
import s03.service.AccountActionControlService;
import s03.service.Service;
import s03.service.UserDTO;
import s03.view.CustomInterface.ActionControlPanel;
import s03.view.EnterUserData.UserDataReceiver;
import s03.view.UserDataControl.ControlExit;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AccountActionControlPanel implements ActionControlPanel {
    private final AccountActionControlService service;
    private final UserDataReceiver userDataReceiver;
    private final ControlExit controlExit;
    private final UserDTO selectedUser;

    public AccountActionControlPanel(UserDTO selectedUser, Service service) {
        this.selectedUser = selectedUser;
        this.service = new AccountActionControlService(service, selectedUser);
        this.userDataReceiver = new UserDataReceiver(service);
        this.controlExit = new ControlExit();
    }

    public void showListAccount() {
        this.service.returnListAccount().stream().forEach(System.out::println);
    }

    @Override
    public void add() throws ActionControlException {
        String name = this.userDataReceiver.enterNameAccount(this.selectedUser.getId(), true);
        if (this.controlExit.isExitAction(name)) {
            return;
        }
        String answer = this.userDataReceiver.enterBalanceAccount();
        if (this.controlExit.isExitAction(answer)) {
            return;
        }
        BigDecimal balance = new BigDecimal(answer).setScale(2, RoundingMode.HALF_UP);
        AccountModel accountModel = new AccountModel(name, balance);
        if (this.service.create(accountModel)) {
            System.out.println("Account: " + name + " - created");
        } else {
            throw new ActionControlException();
        }
    }

    @Override
    public void remove() {
        String name = this.userDataReceiver.enterNameAccount(this.selectedUser.getId(), false);
        if (this.controlExit.isExitAction(name)) {
            return;
        }
        AccountModel account = new AccountModel();
        account.setName(name);
        if (this.service.delete(account)) {
            System.out.println("Account: " + name + " - deleted");
        } else {
            System.out.println("Account: " + name + " - error");
        }
    }
}
