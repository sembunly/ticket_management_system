package com.bunli.common.exception;


import com.bunli.common.constant.ApiConstant;
import com.bunli.common.dto.EmptyObject;

import java.util.ArrayList;
import java.util.List;

public class GeneralErrorResponse {

    public static ResponseErrorTemplate generalError(){
        return new ResponseErrorTemplate(
                ApiConstant.GENERAL_ERROR.getDescription(),
                ApiConstant.GENERAL_ERROR.getKey(),
                new EmptyObject(),
                true);
    }

    public static ResponseErrorTemplate generalErrors(){
        List<EmptyObject> emptyObjectList = new ArrayList<>();
        return new ResponseErrorTemplate(
                ApiConstant.GENERAL_ERROR.getDescription(),
                ApiConstant.GENERAL_ERROR.getKey(),
                emptyObjectList,
                true);
    }

}
