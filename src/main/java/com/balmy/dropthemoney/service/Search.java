package com.balmy.dropthemoney.service;

import com.balmy.dropthemoney.common.response.dto.SingleResult;
import com.balmy.dropthemoney.common.response.service.ResponseService;
import com.balmy.dropthemoney.model.DropMoney;
import com.balmy.dropthemoney.model.ReceiveInfo;
import com.balmy.dropthemoney.model.User;
import com.balmy.dropthemoney.model.dto.DropInfoDTO;
import com.balmy.dropthemoney.model.dto.ReceiveInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class Search {
    static final long SEVEN_DAYS = (60 * 60 * 24 * 7);
    static final String IS_NOT_DROP_USER = "Is not Drop User";
    static final String INVALID_TOKEN = "Invalid token";
    static final String EXPIRED_TOKEN = "Expired token";


    @Autowired
    private ResponseService responseService;

    @Autowired
    private DropMoneyService dropMoneyService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReceiveInfoService receiveInfoService;

    public SingleResult<DropInfoDTO> search(long userId, String token) {
        User user = userService.findById(userId);

        if (dropMoneyService.findByToken(token).size() == 0) {
            //유효하지 않은 토큰 실패 응답
            return responseService.getSingleFailResult(INVALID_TOKEN);
        } else if (!dropMoneyService.isDropUser(token, user)) {
            //자신이 뿌리지 않은 건에 대한 조회 실패 응답
            return responseService.getSingleFailResult(IS_NOT_DROP_USER);
        }

        DropInfoDTO dropInfoDTO = new DropInfoDTO();
        DropMoney dropMoney = dropMoneyService.findByToken(token).get(0);

        if (isExpiredToken(dropMoney.getCreatedAt())) {
            //조회가능 기간이 지난 뿌리기 건에 대한 실패 응답
            return responseService.getSingleFailResult(EXPIRED_TOKEN);
        }

        //뿌리기 시각
        dropInfoDTO.setDropTime(dropMoney.getCreatedAt());
        //뿌린 금액
        dropInfoDTO.setDropMoney(dropMoney.getTotalMoney());
        //받기 완료된 금액
        dropInfoDTO.setReceivedMoney(calReceivedMoney(dropMoney));
        //받기 완료된 정보
        List<ReceiveInfoDTO> receiveInfoDTOS = makeReceiveInfoDTOs(dropMoney);
        dropInfoDTO.setReceiveInfoDTOs(receiveInfoDTOS);

        return responseService.getSingleResult(dropInfoDTO);
    }

    public long calReceivedMoney(DropMoney dropMoney) {
        return receiveInfoService.findByDropMoneyAndReceiveUserIsNotNull(dropMoney).stream().mapToLong(ReceiveInfo::getMoney).sum();
    }

    public List<ReceiveInfoDTO> makeReceiveInfoDTOs(DropMoney dropMoney) {
        List<ReceiveInfoDTO> receiveInfoDTOs = new ArrayList<>();

        receiveInfoService.findByDropMoneyAndReceiveUserIsNotNull(dropMoney).stream().forEach(receiveInfo -> {
            ReceiveInfoDTO receiveInfoDTO = new ReceiveInfoDTO();
            receiveInfoDTO.setReceiveMoney(receiveInfo.getMoney());
            receiveInfoDTO.setReceiveMoney(receiveInfo.getReceiveUser().getId());
        });

        return receiveInfoDTOs;
    }

    public boolean isExpiredToken(LocalDateTime dateTime) {
        if (Duration.between(dateTime, LocalDateTime.now()).getSeconds() > SEVEN_DAYS) {
            return true;
        } else {
            return false;
        }
    }
}
