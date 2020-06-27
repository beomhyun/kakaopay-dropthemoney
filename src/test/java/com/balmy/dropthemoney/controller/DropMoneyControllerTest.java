package com.balmy.dropthemoney.controller;

import com.balmy.dropthemoney.service.Drop;
import com.balmy.dropthemoney.service.Receive;
import com.balmy.dropthemoney.service.Search;
import net.minidev.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DropMoneyController.class)
public class DropMoneyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Drop drop;

    @MockBean
    private Receive receive;

    @MockBean
    private Search search;

    @Test
    public void drop() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", "success");
        map.put("token", "kak");
        JSONObject json = new JSONObject();
        json.putAll(map);


        when(drop.drop(1000, 3, 1, "room001")).thenReturn(json.toString());

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("people", "3");
        params.add("amount", "1000");

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-USER-ID", "1");
        headers.add("X-ROOM-ID", "room001");
        mockMvc.perform(post("/drop").headers(headers).params(params))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void receive() {
    }

    @Test
    public void search() {
    }
}