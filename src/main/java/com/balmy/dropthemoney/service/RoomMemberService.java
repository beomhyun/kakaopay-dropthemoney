package com.balmy.dropthemoney.service;

import com.balmy.dropthemoney.model.Room;
import com.balmy.dropthemoney.model.User;
import com.balmy.dropthemoney.repository.RoomMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomMemberService {

    @Autowired
    private RoomMemberRepository roomMemberRepository;

    public boolean isRoomMember(Room room, User user) {
        if (roomMemberRepository.findByRoomAndUser(room, user).size() == 0) {
            return false;
        } else {
            return true;
        }
    }
}