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
}
