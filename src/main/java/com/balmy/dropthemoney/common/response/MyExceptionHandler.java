package com.balmy.dropthemoney.common.response;

import com.balmy.dropthemoney.common.response.dto.SingleResult;
import com.balmy.dropthemoney.common.response.service.ResponseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class MyExceptionHandler {

    @Autowired
    private ResponseService responseService;

    @ExceptionHandler(Exception.class)
    public SingleResult<Object> handledException(Exception e) {
        log.error("{} -> {}", e.getMessage(), e);
        return responseService.getSingleFailResult(e.getMessage());
    }

}
