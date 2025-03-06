package ru.gnezdilov.dao;

import org.junit.Test;
import ru.gnezdilov.dao.exception.AlreadyExistsException;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.exception.NotFoundException;
import ru.gnezdilov.dao.model.TypeModel;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TypeDAOTest extends AbstractDAOTest<TypeDAO> {
    public TypeDAOTest() {
        setPropertyForConnectH2();
        subj = context.getBean(TypeDAO.class);
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
    public void delete_failedAndReturnFalse_whenCalledWithInvalidArgument() {
        assertFalse(subj.delete(2, 2));
    }

    @Test
    public void getAll_successAndReturnListTypes_whenCalledWithValidArgument() {
        List<TypeModel> typeModels = new ArrayList<TypeModel>();
        typeModels.add(new TypeModel(1, 1, "hobby"));

        assertEquals(typeModels, subj.getAll(1));
    }

    @Test
    public void getAll_failedAndReturnEmptyList_whenCalledWithValidArgument() {
        List<TypeModel> typeModels = new ArrayList<>();
        assertEquals(typeModels, subj.getAll(2));
    }

    @Test
    public void existsById_successAndReturnTypeModel_whenCalledWithValidArgument() {
          assertTrue(subj.existsById(1, 1));
    }

    @Test
    public void existsById_failedAndReturnNotFound_whenCalledWithInvalidArgument() {
        assertFalse(subj.existsById(1, 2));
    }
}
