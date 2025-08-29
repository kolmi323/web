package ru.gnezdilov.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gnezdilov.dao.entities.TransactionModel;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionModel, Integer> {
}
