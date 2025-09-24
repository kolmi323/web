package ru.gnezdilov.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gnezdilov.dao.entities.UserModel;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {
   Optional<UserModel> findByEmailAndPassword(String mail, String password);

   boolean existsById(Integer id);
}
