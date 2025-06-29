package ru.gnezdilov.dao;

import org.springframework.stereotype.Component;
import ru.gnezdilov.dao.exception.AlreadyExistsException;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.entities.TypeModel;
import java.lang.IllegalArgumentException;

import javax.persistence.*;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class TypeDAO {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public TypeModel update(int id, int userId, String newName) {
        try {
            TypeModel typeModel = em.createNamedQuery("Type.findByIdAndUserId", TypeModel.class)
                    .setParameter("id", id)
                    .setParameter("userId", userId)
                    .getSingleResult();
            typeModel.setName(newName);
            em.merge(typeModel);
            return typeModel;
        } catch (NoResultException e) {
            throw new DAOException("Type with id " + id + " not found");
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    @Transactional
    public TypeModel insert(int userId, String name) {
        try {
            Optional<TypeModel> typeCheck = em.createNamedQuery("Type.findByUserIdAndName", TypeModel.class)
                    .setParameter("userId", userId)
                    .setParameter("name", name)
                    .getResultStream()
                    .findFirst();
            if (!typeCheck.isPresent()) {
                TypeModel typeModel = new TypeModel();
                typeModel.setUserId(userId);
                typeModel.setName(name);
                em.persist(typeModel);
                return typeModel;
            } else {
                throw new AlreadyExistsException("Type already exists");
            }
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    @Transactional
    public boolean delete(int id, int userId) {
        try {
            Optional<TypeModel> typeModel = em.createNamedQuery("Type.findByIdAndUserId", TypeModel.class)
                    .setParameter("id", id)
                    .setParameter("userId", userId)
                    .getResultStream()
                    .findFirst();
            if (typeModel.isPresent()) {
                em.remove(typeModel.get());
                return true;
            } else {
                return false;
            }
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    public List<TypeModel> getAll(int userId) {
        try {
           return em.createNamedQuery("Type.findByUserId", TypeModel.class)
                   .setParameter("userId", userId)
                   .getResultList();
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    @Transactional
    public boolean existsById(int userId, int id) {
        try {
            Optional<TypeModel> typeModel = em.createNamedQuery("Type.findByIdAndUserId", TypeModel.class)
                    .setParameter("id", id)
                    .setParameter("userId", userId)
                    .getResultStream()
                    .findFirst();
            return typeModel.isPresent();
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }
}
