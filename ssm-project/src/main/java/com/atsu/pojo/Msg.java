package com.atsu.pojo;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname Msg
 * @author: 我心
 * @Description: 用于服务器与浏览器交互的类，该类封装了交互的额数据以及状态信息，控制信息
 * @Date 2022/7/30 18:50
 * @Created by Lenovo
 */
public class Msg {
    //状态码100-成功，200-失败
    public static final String SUCCESS="100";
    public static final String FAIL="200";
    private String stateCode;//状态码
    private Map<String,Object> dataMap=new HashMap<>();//交互的数据map集合
    private String returnMsg;//返回的状态信息

    public Msg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public Msg() {
    }

    //添加交互数据
    public Msg addAttribute(String key,Object value){
        this.dataMap.put(key,value);
        return this;
    }
    //获取对应的value
    public Object getAttribute(String key){
        Object o = this.dataMap.get(key);
        return o;
    }
    //返回成功的信息对象
    public static Msg successMsg(){
        Msg msg = new Msg();
        msg.setStateCode(Msg.SUCCESS);
        return msg;
    }
    public static Msg successMsg(String returnMsg){
        Msg msg = new Msg();
        msg.setReturnMsg(returnMsg);
        msg.setStateCode(Msg.SUCCESS);
        return msg;
    }
    //失败信息的对象
    public static Msg failMsg(String returnMsg){
        Msg msg = new Msg();
        msg.setReturnMsg(returnMsg);
        msg.setStateCode(Msg.FAIL);
        return msg;
    }
    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public Map<String, Object> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "stateCode='" + stateCode + '\'' +
                ", dataMap=" + dataMap +
                ", returnMsg='" + returnMsg + '\'' +
                '}';
    }
}
