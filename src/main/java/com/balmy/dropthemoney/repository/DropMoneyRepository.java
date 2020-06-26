package com.balmy.dropthemoney.repository;

import com.balmy.dropthemoney.model.DropMoney;
import com.balmy.dropthemoney.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DropMoneyRepository extends JpaRepository<DropMoney, Long> {
    List<DropMoney> findByToken(String token);

    List<DropMoney> findByTokenAndDropUser(String token, User user);
}
