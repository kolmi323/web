package s03.view.PersonalPage;

import s03.CustomException.ActionControlException;
import s03.dao.AccountModel;
import s03.service.ServiceDTO;
import s03.service.UserDTO;
import s03.view.AbstractClass.ActionControlPanel;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AccountActionControlPanel extends ActionControlPanel {
    public AccountActionControlPanel(ServiceDTO service, UserDTO selectedUser) {
        super(service, selectedUser);
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
