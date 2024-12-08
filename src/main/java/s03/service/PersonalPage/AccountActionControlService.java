package s03.service.PersonalPage;

import s03.dao.Model.AccountModel;
import s03.service.AbstractClass.ActionControlService;
import s03.service.DTO.ServiceDTO;
import s03.service.DTO.UserDTO;

import java.util.List;

public class AccountActionControlService extends ActionControlService<AccountModel> {

    public AccountActionControlService(ServiceDTO service, UserDTO currentUser) {
        super(service, currentUser);
    }

    public List<AccountModel> returnListAccount() {
        return managmentDAO.getterDAO.getListAccount(currentUser.getId());
    }

    @Override
    public boolean create(AccountModel account) {
        if (managmentDAO.accountDAO.insertAccount(currentUser.getId(), account)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(AccountModel account) {
        if (managmentDAO.accountDAO.deletedAccount(currentUser.getId(), account)) {
            return true;
        } else {
            return false;
        }
    }
}
