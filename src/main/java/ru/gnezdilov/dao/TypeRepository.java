package ru.gnezdilov.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gnezdilov.dao.entities.TypeModel;
import ru.gnezdilov.dao.exception.DAOException;

import java.util.List;

@Repository
public interface TypeRepository extends JpaRepository<TypeModel, Integer> {
    @Transactional
    @Modifying
    @Query("UPDATE TypeModel t SET t.name = :newName WHERE t.id = :id AND t.userId = :userId")
    int updateName(@Param(value = "id") Integer id, @Param(value = "userId") Integer userId,
                         @Param(value = "newName") String newName) throws DAOException;
    @Transactional
    Integer deleteByIdAndUserId(Integer id, Integer userId) throws DAOException;
    List<TypeModel> getAllByUserId(Integer userId) throws DAOException;
    boolean existsByIdAndUserId(Integer id, Integer userId) throws DAOException;
    TypeModel findByIdAndUserId(Integer id, Integer userId) throws DAOException;

    /*@Transactional
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
                TypeModel type = new TypeModel();
                type.setUserId(userId);
                type.setName(name);
                em.persist(type);
                if (type.getId() != 0) {
                    return type;
                } else {
                    throw new DAOException("Insert Type failed");
                }
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
    }*/
}
