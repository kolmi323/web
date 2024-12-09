package s03.view.PersonalPage;

import s03.CustomException.ActionControlException;
import s03.dao.Model.AccountModel;
import s03.service.DTO.ServiceDTO;
import s03.service.DTO.UserDTO;
import s03.service.PersonalPage.AccountActionControlService;
import s03.view.AbstractClass.ActionControlPanel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class AccountActionControlPanel extends ActionControlPanel<AccountActionControlService> {
    public AccountActionControlPanel(ServiceDTO service, UserDTO selectedUser) {
        super(service, selectedUser);
    }

    @Override
    protected AccountActionControlService createActionControlService(ServiceDTO service, UserDTO selectedUser) {
        return new AccountActionControlService(service, selectedUser);
    }

    public void showListAccount() {
        this.service.returnListAccount().ifPresentOrElse(
                accounts -> accounts.stream().forEach(System.out::println),
                () -> System.out.println("No account found!"));
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
        accountModel.setUserId(selectedUser.getId());
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
        account.setUserId(selectedUser.getId());
        account.setName(name);
        if (this.service.delete(account)) {
            System.out.println("Account: " + name + " - deleted");
        } else {
            System.out.println("Account: " + name + " - not found");
        }
    }
}
