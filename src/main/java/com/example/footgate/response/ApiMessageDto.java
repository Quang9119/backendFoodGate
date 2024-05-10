package com.example.footgate.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiMessageDto<T> {
    private T data = null;

}
