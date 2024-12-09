package s03.view.PersonalPage;

import s03.CustomException.ActionControlException;
import s03.dao.Model.TypeTransactionModel;
import s03.view.AbstractClass.ActionControlPanel;
import s03.service.PersonalPage.TypeTransactionActionControlService;
import s03.service.DTO.ServiceDTO;
import s03.service.DTO.UserDTO;
import s03.view.EnterUserData.FlagEnterNameType;

public class TypeTransactionActionControlPanel extends ActionControlPanel<TypeTransactionActionControlService> {

    public TypeTransactionActionControlPanel(ServiceDTO serviceDTO, UserDTO selectedUser) {
        super(serviceDTO, selectedUser);
    }

    @Override
    protected TypeTransactionActionControlService createActionControlService(ServiceDTO service, UserDTO selectedUser) {
        return new TypeTransactionActionControlService(service, selectedUser);
    }

    public void showListTypeTransaction() {
        this.service.returnListTypeTransactions().ifPresentOrElse(
                transactions -> transactions.stream().forEach(System.out::println),
                () -> System.out.println("No type transactions found!"));
    }

    public void update() throws ActionControlException {
        String oldName = this.userDataReceiver.enterNameType(selectedUser.getId(), FlagEnterNameType.UPDATE);
        if (controlExit.isExitAction(oldName)) {
            return;
        }
        String newName = this.userDataReceiver.enterNameType(selectedUser.getId(), FlagEnterNameType.OTHER);
        if (controlExit.isExitAction(newName)) {
            return;
        }
        if (service.edit(oldName, newName)) {
            System.out.println("Name type transaction edited successfully!");
        } else {
            throw new ActionControlException();
        }
    }

    @Override
    public void add() throws ActionControlException {
        String name = this.userDataReceiver.enterNameType(this.selectedUser.getId(), FlagEnterNameType.CREATE);
        if (this.controlExit.isExitAction(name)) {
            return;
        }
        TypeTransactionModel typeTransactionModel = new TypeTransactionModel();
        typeTransactionModel.setUserId(this.selectedUser.getId());
        typeTransactionModel.setName(name);
        if (service.create(typeTransactionModel)) {
            System.out.println("Type: " + name + " - created");
        } else {
            throw new ActionControlException();
        }
    }

    @Override
    public void remove() {
        String name = this.userDataReceiver.enterNameType(this.selectedUser.getId(), FlagEnterNameType.OTHER);
        if (this.controlExit.isExitAction(name)) {
            return;
        }
        TypeTransactionModel typeTransactionModel = new TypeTransactionModel(name);
        typeTransactionModel.setUserId(this.selectedUser.getId());
        if (service.delete(typeTransactionModel)) {
            System.out.println("Type: " + name + " - deleted");
        } else {
            System.out.println("Type: " + name + " - not found");
        }
    }
}
