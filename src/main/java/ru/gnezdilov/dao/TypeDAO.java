package ru.gnezdilov.dao;

import org.springframework.stereotype.Component;
import ru.gnezdilov.dao.exception.AlreadyExistsException;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.entities.TypeModel;
import java.lang.IllegalArgumentException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TypeDAO extends DAO {

    public TypeDAO(DataSource ds, EntityManagerFactory emf) {
        super(ds, emf);
    }

    @Transactional
    public TypeModel update(int id, int userId, String newName) {
        try {
            TypeModel typeModel = em.createNamedQuery("Type.findByIdAndUserId", TypeModel.class)
                    .setParameter("id", id)
                    .setParameter("userId", userId)
                    .getSingleResult();
            typeModel.setName(newName);
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.merge(typeModel);
            tx.commit();
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
            TypeModel typeModel = new TypeModel();
            typeModel.setUserId(userId);
            typeModel.setName(name);
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(typeModel);
            tx.commit();
            if (typeModel.getId() != 0) {
                return typeModel;
            } else {
                throw new DAOException("Insert Type Error");
            }
        } catch (PersistenceException e) {
            if (isUniqueSQLState(e)) {
                throw new AlreadyExistsException("Type already exists");
            } else {
                throw new DAOException(e);
            }
        }
    }

    @Transactional
    public boolean delete(int id, int userId) {
        try {
            TypeModel typeModel = em.createNamedQuery("Type.findByIdAndUserId", TypeModel.class)
                    .setParameter("id", id)
                    .setParameter("userId", userId)
                    .getSingleResult();
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.remove(typeModel);
            tx.commit();
            return true;
        } catch (NoResultException e) {
           return false;
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

    public boolean existsById(int userId, int id) {
        try {
            TypeModel typeModel = em.createNamedQuery("Type.findByIdAndUserId", TypeModel.class)
                    .setParameter("id", id)
                    .setParameter("userId", userId)
                    .getSingleResult();
            return true;
        } catch (NoResultException e) {
          return false;
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }
}
