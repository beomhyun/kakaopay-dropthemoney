package com.balmy.dropthemoney.service;

import com.balmy.dropthemoney.model.DropMoney;
import com.balmy.dropthemoney.model.ReceiveInfo;
import com.balmy.dropthemoney.model.User;
import com.balmy.dropthemoney.repository.ReceiveInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiveInfoService {

    @Autowired
    private ReceiveInfoRepository receiveInfoRepository;

    public ReceiveInfo save(ReceiveInfo receiveInfo) {
        return receiveInfoRepository.save(receiveInfo);
    }

    public List<ReceiveInfo> saveReceiveInfoList(List<ReceiveInfo> receiveInfos) {
        return receiveInfoRepository.saveAll(receiveInfos);
    }

    public List<ReceiveInfo> findByDropMoneyAndReceiveUserIsNull(DropMoney dropMoney) {
        return receiveInfoRepository.findByDropMoneyAndReceiveUserIsNull(dropMoney);
    }

    public List<ReceiveInfo> findByDropMoneyAndReceiveUserIsNotNull(DropMoney dropMoney) {
        return receiveInfoRepository.findByDropMoneyAndReceiveUserIsNotNull(dropMoney);
    }

    public boolean isAlreadyReceived(DropMoney dropMoney, User user) {
        if (receiveInfoRepository.findByDropMoneyAndReceiveUser(dropMoney, user).size() == 0) {
            return false;
        } else {
            return true;
        }
    }
}
