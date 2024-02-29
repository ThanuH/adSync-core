package com.project.adsync.repository;

import com.project.adsync.domain.User;
import com.project.adsync.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
    Optional<User> findByEmailAndPassword(String email, String password);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findUserByEmail(String email);

    @Query("SELECT count(*) FROM User u WHERE u.userRole = :userRole")
    int getUserCountByType(UserRole userRole);

    @Query("SELECT u FROM User u WHERE u.id = :userId")
    User getUserById(int userId);



    //int getUserCountByType(UserRole userRole);*/
}
