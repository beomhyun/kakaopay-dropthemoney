package com.balmy.dropthemoney.service;

import com.balmy.dropthemoney.model.ReceiveInfo;
import com.balmy.dropthemoney.model.Room;
import com.balmy.dropthemoney.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class DropTest {

    @Mock
    private ReceiveInfoService receiveInfoService = mock(ReceiveInfoService.class);

    @Mock
    private DropMoneyService dropMoneyService = mock(DropMoneyService.class);

    @Mock
    private UserService userService = mock(UserService.class);

    @Mock
    private RoomService roomService = mock(RoomService.class);

    @InjectMocks
    Drop drop = mock(Drop.class);

    @Test
    void drop_when_all_parameter_is_OK() {
        //given
        String token = "abc";
        long amount = 10;
        long people = 3;
        long userId = 1;
        String roomID = "room001";

        List<ReceiveInfo> receiveInfos = new ArrayList<>();
        User user = new User();
        user.setId(userId);
        Room room = new Room();
        room.setId(roomID);

        //when
        when(receiveInfoService.saveReceiveInfoList(any())).thenReturn(receiveInfos);
        when(userService.findById(anyLong())).thenReturn(user);
        when(roomService.findById(anyString())).thenReturn(room);
        when(drop.makeToken()).thenReturn(token);
        when(dropMoneyService.findByToken(anyString())).thenReturn(new ArrayList<>());
        when(userService.save(any())).thenReturn(user);
        when(drop.drop(amount, people, userId, roomID)).thenReturn(token);
        //then
        Assertions.assertEquals(token, drop.drop(amount, people, userId, roomID));

    }
}