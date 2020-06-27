package com.balmy.dropthemoney;

import com.balmy.dropthemoney.model.Room;
import com.balmy.dropthemoney.model.RoomMember;
import com.balmy.dropthemoney.model.User;
import com.balmy.dropthemoney.service.RoomMemberService;
import com.balmy.dropthemoney.service.RoomService;
import com.balmy.dropthemoney.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomMemberService roomMemberService;

    //사용자, 대화방 초기 세팅
    public void run(ApplicationArguments args) {

        Room room = new Room();
        roomService.save(room);

        User user = new User();
        user.setBalance(10);
        user.setName("user1");
        userService.save(user);
        RoomMember roomMember = new RoomMember();
        roomMember.setRoom(room);
        roomMember.setUser(user);
        roomMemberService.save(roomMember);

        user = new User();
        user.setName("user2");
        userService.save(user);
        roomMember = new RoomMember();
        roomMember.setRoom(room);
        roomMember.setUser(user);
        roomMemberService.save(roomMember);


    }
}