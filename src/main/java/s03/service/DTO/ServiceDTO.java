package s03.service.DTO;

import s03.dao.ManagmentDAO;
import s03.service.AbstractClass.Service;

public class ServiceDTO extends Service {

    public ServiceDTO() {
        this.managmentDAO = new ManagmentDAO();
    }

    @Override
    protected ManagmentDAO getManagmentDAO() {
        return super.getManagmentDAO();
    }
}
