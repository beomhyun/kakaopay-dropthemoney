package com.balmy.dropthemoney.common.response.dto;

import lombok.Data;

@Data
public class CommonResult {

    private boolean success;

    private int code;

    private String msg;
}