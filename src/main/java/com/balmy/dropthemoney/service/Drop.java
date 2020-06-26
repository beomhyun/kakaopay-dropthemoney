package com.balmy.dropthemoney.service;

import com.balmy.dropthemoney.model.DropMoney;
import com.balmy.dropthemoney.model.ReceiveInfo;
import com.balmy.dropthemoney.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class Drop {

    static final int ASCII_MIN_INDEX = 33;//!
    static final int ASCII_MAX_INDEX = 126;//~
    static final int TOKEN_LENGTH = 3;

    @Autowired
    private ReceiveInfoService receiveInfoService;

    @Autowired
    private DropMoneyService dropMoneyService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @Transactional
    public String drop(long amount, long people, long userId, String roomId) {
        DropMoney dropMoney = makeReceiveInfos(amount, people);
        dropMoney.setDropUser(userService.findById(userId));
        dropMoney.setRoom(roomService.findById(roomId));

        String token = makeToken();
        while (!isCanUseToken(token)) {
            token = makeToken();
        }
        dropMoney.setToken(token);
        dropMoneyService.saveDropMoney(dropMoney);

        withdrawMoney(userId, amount);
        return token;
    }

    private void withdrawMoney(long userId, long amount) {
        User user = userService.findById(userId);
        user.setBalance(userService.findById(userId).getBalance() - amount);
        userService.save(user);
    }

    private boolean isCanUseToken(String token) {
        if (dropMoneyService.findByToken(token).size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    private DropMoney makeReceiveInfos(long amount, long people) {
        DropMoney dropMoney = new DropMoney();
        dropMoney.setTotalMoney(amount);
        List<ReceiveInfo> receiveInfos = distributeAmount(amount, people);

        receiveInfoService.saveReceiveInfoList(receiveInfos);
        dropMoney.setReceiveInfos(receiveInfos);
        return dropMoney;
    }

    private List<ReceiveInfo> distributeAmount(long amount, long people) {
        List<ReceiveInfo> receiveInfos = new ArrayList<>();
        long tempAmount = amount;

        for (int i = 0; i < people; i++) {
            ReceiveInfo tempReceiveInfo = new ReceiveInfo();
            tempReceiveInfo.setMoney(1);
            tempAmount--;
            receiveInfos.add(tempReceiveInfo);
        }
        int index = 0;
        Random random = new Random();
        while (tempAmount > 0) {
            long tempMoney = random.nextInt((int) tempAmount) + 1;
            ReceiveInfo tempReceiveInfo = receiveInfos.get(index++);
            tempReceiveInfo.setMoney(tempReceiveInfo.getMoney() + tempMoney);
            tempAmount -= tempMoney;

            if (index == receiveInfos.size()) {
                index = 0;
            }
        }
        return receiveInfos;
    }

    public String makeToken() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < TOKEN_LENGTH; i++) {
            char tempChar = (char) (random.nextInt(ASCII_MAX_INDEX - ASCII_MIN_INDEX + 1) + ASCII_MIN_INDEX);
            sb.append(tempChar);
        }
        return sb.toString();
    }
}
