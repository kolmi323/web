package ru.gnezdilov.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.gnezdilov.dao.entities.CategoryTransactionModel;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CategoryTransactionRepository extends JpaRepository<CategoryTransactionModel, Integer> {
    @Query(value = "SELECT COALESCE(ty.name, 'no type'), SUM(tr.amount) " +
            "FROM UserModel us " +
            "JOIN AccountModel ac ON us.id = ac.userId " +
            "JOIN TransactionModel tr ON ac.id = tr.receiverAccountId " +
            "LEFT JOIN CategoryTransactionModel tt ON tr.id = tt.transaction.id " +
            "LEFT JOIN TypeModel ty ON tt.typeId = ty.id " +
            "WHERE us.id = :userId AND tr.date > :dateAfter AND tr.date < :dateBefore " +
            "GROUP BY ty.name")
    List<Object[]> getIncomingTransaction(@Param("userId") Integer userId,
                                          @Param("dateAfter") LocalDate dateAfter,
                                          @Param("dateBefore") LocalDate dateBefore);

    @Query(value = "SELECT COALESCE(ty.name, 'no type'), SUM(tr.amount) " +
            "FROM UserModel us " +
            "JOIN AccountModel ac ON us.id = ac.userId " +
            "JOIN TransactionModel tr ON ac.id = tr.senderAccountId " +
            "LEFT JOIN CategoryTransactionModel tt ON tr.id = tt.transaction.id " +
            "LEFT JOIN TypeModel ty ON tt.typeId = ty.id " +
            "WHERE us.id = :userId AND tr.date > :dateAfter AND tr.date < :dateBefore " +
            "GROUP BY ty.name")
    List<Object[]> getOutgoingTransaction(@Param("userId") Integer userId,
                                          @Param("dateAfter") LocalDate dateAfter,
                                          @Param("dateBefore") LocalDate dateBefore);
}
