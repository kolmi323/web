package ru.gnezdilov.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import ru.gnezdilov.WebApplication;
import ru.gnezdilov.dao.entities.TypeModel;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@DataJpaTest
@ContextConfiguration(classes = WebApplication.class)
public class TypeRepositoryTest {
    @Autowired
    private TypeRepository subj;

    @Test
    public void deleteByIdAndUserId() {
        assertEquals(subj.deleteByIdAndUserId(1, 1), (Integer) 1);
    }

    @Test
    public void getAllByUserId_returnListType_whenCalledWithValidArgument() {
        List<TypeModel> list = new ArrayList<>();
        list.add(new TypeModel(1, 1, "hobby"));

        assertEquals(subj.getAllByUserId(1), list);
    }

    @Test
    public void existsByIdAndUserId_returnTrue_whenCalledWithValidArgument() {
        assertTrue(subj.existsByIdAndUserId(1, 1));
    }

    @Test
    public void findByIdAndUserId_returnType_whenCalledWithValidArgument() {
        assertEquals(subj.findByIdAndUserId(1, 1), new TypeModel(1, 1, "hobby"));
    }
}