package com.balmy.dropthemoney.controller;

import com.balmy.dropthemoney.common.response.dto.SingleResult;
import com.balmy.dropthemoney.common.response.service.ResponseService;
import com.balmy.dropthemoney.model.dto.DropInfoDTO;
import com.balmy.dropthemoney.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RestController
@Slf4j
public class DropMoneyController {
    static final String USER_ID_HEADER = "X-USER-ID";
    static final String ROOM_ID_HEADER = "X-ROOM-ID";

    @Autowired
    private Drop drop;

    @Autowired
    private Receive receive;

    @Autowired
    private Search search;

    @Autowired
    private ResponseService responseService;

    @PostMapping(value = "/drop")
    public SingleResult<String> drop(@RequestParam long amount, @RequestParam long people, @RequestHeader HttpHeaders headers) throws Exception {
        long userId = Long.parseLong(headers.get(USER_ID_HEADER).get(0));
        String roomId = headers.get(ROOM_ID_HEADER).get(0);

        return responseService.getSingleResult(drop.drop(amount, people, userId, roomId));
    }

    @PostMapping(value = "/receive")
    public SingleResult<Long> receive(@RequestBody HashMap<String, String> requestBody, @RequestHeader HttpHeaders headers) throws Exception {
        long userId = Long.parseLong(headers.get(USER_ID_HEADER).get(0));
        String roomId = headers.get(ROOM_ID_HEADER).get(0);
        String token = requestBody.get("token");

        return receive.receive(roomId, userId, token);
    }

    @GetMapping(value = "/search")
    public SingleResult<DropInfoDTO> search(@RequestBody HashMap<String, String> requestBody, @RequestHeader HttpHeaders headers) throws Exception {
        long userId = Long.parseLong(headers.get(USER_ID_HEADER).get(0));
        String token = requestBody.get("token");
        return search.search(userId, token);
    }

}