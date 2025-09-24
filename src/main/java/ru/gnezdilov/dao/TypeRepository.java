package ru.gnezdilov.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gnezdilov.dao.entities.TypeModel;

import java.util.List;

@Repository
public interface TypeRepository extends JpaRepository<TypeModel, Integer> {
    @Transactional
    Integer deleteByIdAndUserId(Integer id, Integer userId);

    List<TypeModel> getAllByUserId(Integer userId);

    boolean existsByIdAndUserId(Integer id, Integer userId);

    TypeModel findByIdAndUserId(Integer id, Integer userId);
}
