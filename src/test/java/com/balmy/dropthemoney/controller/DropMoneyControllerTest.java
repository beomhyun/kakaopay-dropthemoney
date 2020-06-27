package com.balmy.dropthemoney.controller;

import com.balmy.dropthemoney.service.Drop;
import com.balmy.dropthemoney.service.Receive;
import com.balmy.dropthemoney.service.Search;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

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
    }

    @Test
    public void receive() {
    }

    @Test
    public void search() {
    }
}