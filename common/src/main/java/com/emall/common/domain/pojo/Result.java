package com.emall.common.domain.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Integer code;
    private String message;
    private HashMap<String, Object> data = new HashMap<>();

    public static Result success(){
        Result result = new Result();
        result.setCode(200);
        return result;
    }

    public static Result fail(){
        Result result = new Result();
        result.setCode(400);
        return result;
    }

    public Result addMsg(String message){
        this.setMessage(message);
        return this;
    }

    public Result addData(String key, Object value){
        this.getData().put(key, value);
        return this;
    }
}

