package com.balmy.dropthemoney.repository;

import com.balmy.dropthemoney.model.DropMoney;
import com.balmy.dropthemoney.model.ReceiveInfo;
import com.balmy.dropthemoney.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceiveInfoRepository extends JpaRepository<ReceiveInfo, Long> {
    List<ReceiveInfo> findByDropMoneyAndReceiveUser(DropMoney dropmoney, User user);

    List<ReceiveInfo> findByDropMoneyAndReceiveUserIsNull(DropMoney dropmoney);

    List<ReceiveInfo> findByDropMoneyAndReceiveUserIsNotNull(DropMoney dropmoney);

}
