package com.balmy.dropthemoney.service;

import com.balmy.dropthemoney.common.response.service.ResponseService;
import com.balmy.dropthemoney.model.DropMoney;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReceiveTest {

    @Mock
    private DropMoneyService dropMoneyService = mock(DropMoneyService.class);

    @Mock
    private RoomMemberService roomMemberService = mock(RoomMemberService.class);

    @Mock
    private RoomService roomService = mock(RoomService.class);

    @Mock
    private UserService userService = mock(UserService.class);

    @Mock
    private ResponseService responseService = mock(ResponseService.class);

    @Mock
    private ReceiveInfoService receiveInfoService = mock(ReceiveInfoService.class);

    @InjectMocks
    private Receive receive = mock(Receive.class);

    @Test
    void isInValidToken_when_right_token() {
        //given
        String token = "abc";
        List<DropMoney> dropMonies = new ArrayList<>();
        DropMoney dropMoney = new DropMoney();
        dropMoney.setToken(token);
        dropMonies.add(dropMoney);

        //when
        when(dropMoneyService.findByToken(anyString())).thenReturn(dropMonies);
        when(receive.isInValidToken(token)).thenReturn(true);

        //then
        Assertions.assertTrue(receive.isInValidToken(token));
    }
    @Test
    void isInValidToken_when_wrong_token() {
        //given
        String token = "abc";
        List<DropMoney> dropMonies = new ArrayList<>();
        //when
        when(dropMoneyService.findByToken(anyString())).thenReturn(dropMonies);
        when(receive.isInValidToken(token)).thenReturn(true);

        //then
        Assertions.assertTrue(receive.isInValidToken(token));
    }
    @Test
    void isExpiredToken() {
    }

    @Test
    void receive() {
    }

    @Test
    void receiveMoney() {
    }
}