package com.balmy.dropthemoney.service;

import com.balmy.dropthemoney.common.response.dto.SingleResult;
import com.balmy.dropthemoney.common.response.service.ResponseService;
import com.balmy.dropthemoney.model.DropMoney;
import com.balmy.dropthemoney.model.ReceiveInfo;
import com.balmy.dropthemoney.model.Room;
import com.balmy.dropthemoney.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
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
    private RoomMemberService roomMemberService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;

    @Autowired
    private ResponseService responseService;

    @Autowired
    private ReceiveInfoService receiveInfoService;

    public boolean isInValidToken(String token) {
        if (dropMoneyService.findByToken(token).size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isExpiredToken(LocalDateTime dateTime) {
        if (Duration.between(dateTime, LocalDateTime.now()).getSeconds() > TEN_MINUTE) {
            return true;
        } else {
            return false;
        }
    }

    public SingleResult<Long> receive(String roomId, long userId, String token) {
        Room room = roomService.findById(roomId);
        User user = userService.findById(userId);

        if (!roomMemberService.isRoomMember(room, user)) {
            //대화방에 속하지 않은 유저의 요청 실패 응답
            return responseService.getSingleFailResult(NOT_MEMBER);
        } else if (dropMoneyService.isDropUser(token, user)) {
            //자신이 뿌리기한 건 실패 응닫
            return responseService.getSingleFailResult(DROP_USER);
        }

        if (isInValidToken(token)) {
            //유효하지 않은 토큰 실패 응답
            return responseService.getSingleFailResult(INVALID_TOKEN);
        } else {
            DropMoney dropMoney = dropMoneyService.findByToken(token).get(0);
            if (receiveInfoService.isAlreadyReceived(dropMoney, user)) {
                //이미 받은 유저 실패 응답
                return responseService.getSingleFailResult(ALREADY_RECEIVE_USER);
            }
            if (isExpiredToken(dropMoney.getCreatedAt())) {
                //10분이 지난 뿌리기 실패 응답
                return responseService.getSingleFailResult(EXPIRED_TOKEN);
            } else {
                long getMoney = receiveMoney(dropMoney, user);
                if (getMoney == 0) {
                    //이미 다 받은 뿌리기 실패 응답
                    return responseService.getSingleFailResult(ALREADY_SPENT_ALL_MONEY);
                }
                return responseService.getSingleResult(getMoney);
            }
        }
    }

    public long receiveMoney(DropMoney dropMoney, User user) {
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