package com.iware.webshiro.common;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * Created by Mahone on 2016/11/1.
 */
public class ResultParamError {

    public static String dealCommonValidateError(BindingResult result){
        String message = "";
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            if(null != list && list.size() > 0){
                if(StringUtils.isBlank(message)){
                    message = message + list.get(0).getDefaultMessage();
                }else{
                    message = message + "," +list.get(0).getDefaultMessage();
                }
            }
        }
        return message;
    }
}
