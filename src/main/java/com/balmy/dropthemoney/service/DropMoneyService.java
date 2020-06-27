package com.balmy.dropthemoney.service;

import com.balmy.dropthemoney.model.DropMoney;
import com.balmy.dropthemoney.model.User;
import com.balmy.dropthemoney.repository.DropMoneyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DropMoneyService {

    @Autowired
    private DropMoneyRepository dropMoneyRepository;

    public DropMoney saveDropMoney(DropMoney dropMoney) {
        return dropMoneyRepository.save(dropMoney);
    }

    public List<DropMoney> findByToken(String token) {
        return dropMoneyRepository.findByToken(token);
    }

    public boolean isDropUser(String token, User user) {
        System.out.println(dropMoneyRepository.findByTokenAndDropUser(token, user).size());
        if (dropMoneyRepository.findByTokenAndDropUser(token, user).size() == 0) {
            return false;
        } else {
            return true;
        }
    }
}