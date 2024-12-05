package s03.service;

import s03.dao.AccountModel;
import s03.service.CustomInterface.ActionControlService;

import java.math.BigDecimal;
import java.util.List;

public class AccountActionControlService extends Service implements ActionControlService<AccountModel> {
    private final UserDTO currentUser;

    public AccountActionControlService(Service service, UserDTO currentUser) {
        this.userDao = service.getUserDao();
        this.currentUser = currentUser;
    }

    public List<AccountModel> returnListAccount() {
        return userDao.getListAccount(currentUser.getId());
    }

    @Override
    public boolean create(AccountModel account) {
        if (userDao.insertAccount(currentUser.getId(), account)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(AccountModel account) {
        if (userDao.deletedAccount(currentUser.getId(), account)) {
            return true;
        } else {
            return false;
        }
    }
}
