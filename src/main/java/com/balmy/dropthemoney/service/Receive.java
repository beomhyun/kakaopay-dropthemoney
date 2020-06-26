package com.balmy.dropthemoney.service;

import com.balmy.dropthemoney.common.response.dto.SingleResult;
import com.balmy.dropthemoney.common.response.service.ResponseService;
import com.balmy.dropthemoney.model.DropMoney;
import com.balmy.dropthemoney.model.ReceiveInfo;
import com.balmy.dropthemoney.model.Room;
import com.balmy.dropthemoney.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class Receive {
    static final long TEN_MINUTE = (60 * 10);
    static final String INVALID_TOKEN = "Invalid token";
    static final String EXPIRED_TOKEN = "Expired token";
    static final String NOT_MEMBER = "Not member";
    static final String DROP_USER = "Drop user";
    static final String ALREADY_RECEIVE_USER = "Already received user";
    static final String ALREADY_SPENT_ALL_MONEY = "Already spent all money";
    @Autowired
    private DropMoneyService dropMoneyService;

    @Autowired
    private Receive receive;

    @Autowired
    private RoomMemberService roomMemberService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;

    @Autowired
    private ResponseService responseService;

    @Autowired
    private ReceiveInfoService receiveInfoService;

    private boolean isInValidToken(String token) {
        if (dropMoneyService.findByToken(token).size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isExpiredToken(LocalDateTime dateTime) {
        if (Duration.between(dateTime, LocalDateTime.now()).getSeconds() > TEN_MINUTE) {
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public SingleResult<Long> receive(String roomId, long userId, String token) {
        Room room = roomService.findById(roomId);
        User user = userService.findById(userId);

        if (!roomMemberService.isRoomMember(room, user)) {
            return responseService.getSingleFailResult(NOT_MEMBER);
        } else if (dropMoneyService.isDropUser(token, user)) {
            return responseService.getSingleFailResult(DROP_USER);
        }

        if (receive.isInValidToken(token)) {
            return responseService.getSingleFailResult(INVALID_TOKEN);
        } else {
            DropMoney dropMoney = dropMoneyService.findByToken(token).get(0);
            if (receiveInfoService.isAlreadyReceived(dropMoney, user)) {
                return responseService.getSingleFailResult(ALREADY_RECEIVE_USER);
            }
            if (receive.isExpiredToken(dropMoney.getCreatedAt())) {
                return responseService.getSingleFailResult(EXPIRED_TOKEN);
            } else {
                long getMoney = receiveMoney(dropMoney, user);
                if (getMoney == 0) {
                    return responseService.getSingleFailResult(ALREADY_SPENT_ALL_MONEY);
                }
                return responseService.getSingleResult(getMoney);
            }
        }
    }

    private long receiveMoney(DropMoney dropMoney, User user) {
        List<ReceiveInfo> receiveInfos = receiveInfoService.findByDropMoneyAndReceiveUserIsNull(dropMoney);
        if (receiveInfos.size() == 0) {
            return 0;
        }

        Random random = new Random();
        ReceiveInfo receiveInfo = receiveInfos.get(random.nextInt(receiveInfos.size()));

        receiveInfo.setReceiveUser(user);
        receiveInfoService.save(receiveInfo);

        user.setBalance(user.getBalance() + receiveInfo.getMoney());
        userService.save(user);

        return receiveInfo.getMoney();
    }
}