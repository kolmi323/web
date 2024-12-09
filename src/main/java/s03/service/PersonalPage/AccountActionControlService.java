package s03.service.PersonalPage;

import s03.dao.Model.AccountModel;
import s03.service.AbstractClass.ActionControlService;
import s03.service.DTO.ServiceDTO;
import s03.service.DTO.UserDTO;

import java.util.List;
import java.util.Optional;

public class AccountActionControlService extends ActionControlService<AccountModel> {
    public AccountActionControlService(ServiceDTO service, UserDTO currentUser) {
        super(service, currentUser);
    }

    public Optional<List<AccountModel>> returnListAccount() {
        return managmentDAO.getterDAO.getListAccount(currentUser.getId());
    }

    @Override
    public boolean create(AccountModel account) {
        return managmentDAO.accountDAO.insertAccount(account);
    }

    @Override
    public boolean delete(AccountModel account) {
        return managmentDAO.accountDAO.deleteAccount(account);
    }
}
