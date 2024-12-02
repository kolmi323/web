package s03.service;

import s03.dao.AccountModel;

import java.math.BigDecimal;
import java.util.List;

public class PersonOfficeActionService extends Service {
    private final UserDTO currentUser;

    public PersonOfficeActionService(Service service, UserDTO currentUser) {
        this.userDao = service.getUserDao();
        this.currentUser = currentUser;
    }

    public List<AccountModel> returnListAccount() {
        return userDao.getListAccount(currentUser.getId());
    }

    public boolean createAccount(String name, BigDecimal balance) {
        AccountModel account = new AccountModel(name, balance);
        if (userDao.insertAccount(currentUser.getId(), account)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteAccount(int user_id, String name) {
        AccountModel account = new AccountModel();
        account.setName(name);
        if (userDao.deletedAccount(user_id, account)) {
            return true;
        } else {
            return false;
        }
    }
}
