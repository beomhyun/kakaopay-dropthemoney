package com.balmy.dropthemoney.service;

import com.balmy.dropthemoney.model.Room;
import com.balmy.dropthemoney.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserService userService;

    public Room findById(String id) {
        return roomRepository.getOne(id);
    }
    public Room save(Room room) {
        return roomRepository.save(room);
    }
}
