package com.multiGenesys.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse <T>{
    private int statusCode;
    private String message;
    private T result;

    public void responseMethod(int statusCode, String message, T result) {
        this.statusCode = statusCode;
        this.setMessage(message);
        this.setResult(result);
    }
}