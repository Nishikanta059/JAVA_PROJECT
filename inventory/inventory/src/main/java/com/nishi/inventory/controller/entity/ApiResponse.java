package com.nishi.inventory.controller.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse {

    private int status;
    private String message;
    private Object data;
}
