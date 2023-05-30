package com.atsu.util;

/**
 * @Classname NullUtil
 * @author: 我心
 * @Description:
 * @Date 2022/7/29 19:31
 * @Created by Lenovo
 */
public class NullUtil {
    //传入一个默认值，防止为空值
    public static Integer getNotNullInt(Integer data,Integer defaultData){
        if (data==null)
            return defaultData;
        return data;
    }
}
