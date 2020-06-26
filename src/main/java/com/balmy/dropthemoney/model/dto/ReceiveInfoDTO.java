package com.balmy.dropthemoney.model.dto;

import lombok.Data;

@Data
public class ReceiveInfoDTO {
//    받기 완료된 정보 ([받은 금액, 받은 사용자 아이디]
    private long receiveMoney;

    private long receiveUserId;
}
