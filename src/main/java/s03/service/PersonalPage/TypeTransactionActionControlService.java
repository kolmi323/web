package s03.service.PersonalPage;

import s03.dao.Model.TypeTransactionModel;
import s03.service.AbstractClass.ActionControlService;
import s03.service.DTO.ServiceDTO;
import s03.service.DTO.UserDTO;

import java.util.List;
import java.util.Optional;

public class TypeTransactionActionControlService extends ActionControlService<TypeTransactionModel> {
    public TypeTransactionActionControlService(ServiceDTO service, UserDTO currentUser) {
        super(service, currentUser);
    }

    public Optional<List<TypeTransactionModel>> returnListTypeTransactions() {
        return managmentDAO.getterDAO.getListTypeTransaction(currentUser.getId());
    }

    public boolean edit(String oldName, String newName) {
        Optional<List<TypeTransactionModel>> types = returnListTypeTransactions();
        if (types.isPresent()) {
            TypeTransactionModel typeTransactionModel = new TypeTransactionModel(oldName);
            typeTransactionModel.setUserId(currentUser.getId());
            if (managmentDAO.typeTransactionDAO.updateType(typeTransactionModel, newName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean create(TypeTransactionModel model) {
        return managmentDAO.typeTransactionDAO.insertType(model);
    }

    @Override
    public boolean delete(TypeTransactionModel model) {
        return managmentDAO.typeTransactionDAO.deleteType(model);
    }
}
