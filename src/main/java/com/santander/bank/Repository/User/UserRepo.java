package com.santander.bank.Repository.User;

import com.santander.bank.Models.Users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, String> {

    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE(cpf=?1)")
    User findByUserCpf(String cpf);

    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE(account_id = ?1)")
    User findByAccountId(String id);

    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE JSON_CONTAINS(card_id, ?1)")
    User findByCardId(String id);

    UserDetails findByCpf(String cpf);
}




