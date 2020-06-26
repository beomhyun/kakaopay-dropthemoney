package com.balmy.dropthemoney.controller;

import com.balmy.dropthemoney.common.response.service.ResponseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class DropMoneyControllerTest {
    @Autowired
    DropMoneyController dropMoneyController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ResponseService responseService;

    @Test
    void drop_when_success() throws Exception {

        mockMvc.perform(post("/drop")
                .header("X-USER-ID", 1)
                .header("X-ROOM-ID", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void receive() {
    }

    @Test
    void search() {
    }
}