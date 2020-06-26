package com.balmy.dropthemoney.common.response.dto;

import lombok.Data;

@Data
public class SingleResult<T> extends CommonResult {
    private T data;
}