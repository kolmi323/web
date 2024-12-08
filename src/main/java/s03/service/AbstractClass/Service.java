package s03.service.AbstractClass;

import s03.dao.ManagmentDAO;

public abstract class Service {
    protected ManagmentDAO managmentDAO;

    protected Service() {}

    public Service(Service service) {
        this.managmentDAO = service.getManagmentDAO();
    }

    protected ManagmentDAO getManagmentDAO() {
        return managmentDAO;
    }
}
