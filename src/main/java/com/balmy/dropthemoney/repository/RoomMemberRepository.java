package com.balmy.dropthemoney.repository;

import com.balmy.dropthemoney.model.Room;
import com.balmy.dropthemoney.model.RoomMember;
import com.balmy.dropthemoney.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomMemberRepository extends JpaRepository<RoomMember, Long> {
    List<RoomMember> findByRoomAndUser(Room room, User user);
}
