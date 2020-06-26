package com.balmy.dropthemoney.model.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class DropInfoDTO {
    //뿌린 시각, 뿌린 금액, 받기 완료된 금액, 받기 완료된 정보 ([받은 금액, 받은 사용자 아이디] 리스트)

    private LocalDateTime dropTime;

    private long dropMoney;

    private long receivedMoney;

    private List<ReceiveInfoDTO> receiveInfoDTOs;

}
