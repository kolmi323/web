package s03.service;

import s03.dao.AccountModel;
import s03.service.AbstractClass.ActionControlService;

import java.util.List;

public class AccountActionControlService extends ActionControlService<AccountModel> {

    public AccountActionControlService(ServiceDTO service, UserDTO currentUser) {
        super(service, currentUser);
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
