package com.santander.bank.Repository.User;

import com.santander.bank.Models.Users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, String> {

    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE(cpf=?1)")
    User findByUserCpf(String cpf);

    @Query(nativeQuery = true, value =
            "SELECT u.id, u.name, u.account_id, u.card_id, u.password, u.cpf, u.role " +
            "FROM users u " +
            "INNER JOIN accounts a " +
            "ON (a.id=u.account_id) " +
            "WHERE(u.account_id=:accountID);")
    Object[] findByAccountId(@Param("accountID") String id);

    UserDetails findByCpf(String cpf);
}




