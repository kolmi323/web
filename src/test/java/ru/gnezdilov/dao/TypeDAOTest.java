package ru.gnezdilov.dao;

import org.junit.Test;
import ru.gnezdilov.dao.exception.AlreadyExistsException;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.model.TypeModel;

import static org.junit.Assert.*;

public class TypeDAOTest extends AbstractDAOTest<TypeDAO> {
    public TypeDAOTest() {
        setPropertyForConnectH2();
        subj = DaoFactory.getInstance().getTypeDao();
    }

    @Test
    public void update_successUpdateAndReturn_whenCalledWithValidArgument() {
        TypeModel typeModel = new TypeModel(1, 1, "family");
        TypeModel type = subj.update(1, 1, "family");

        assertEquals(typeModel, type);
    }

    @Test (expected = DAOException.class)
    public void update_failedUpdateAndReturnDaoException_whenCalledWithInvalidArgument() {
        TypeModel type = subj.update(2, 2, "travel");
    }

    @Test
    public void insert_successInsertAndReturnTypeModel_whenCalledWithValidArgument() {
        TypeModel typeModel = new TypeModel(2, 1, "travel");
        TypeModel type = subj.insert(1, "travel");

        assertEquals(typeModel.getUserId(), type.getUserId());
        assertEquals(typeModel.getName(), type.getName());
    }

    @Test (expected = AlreadyExistsException.class)
    public void insert_failedSuccessInsertAndReturnAlreadyExistingTypeModel_whenCalledWithInvalidArgument() {
        TypeModel type = subj.insert(1, "hobby");
    }

    @Test
    public void delete_successDeleteAndReturnTrue_whenCalledWithValidArgument() {
        assertTrue(subj.delete(1, 1));
    }

    @Test
    public void getAll() {
    }
}