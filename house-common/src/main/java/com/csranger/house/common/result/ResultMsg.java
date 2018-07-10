package com.csranger.house.common.result;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 *
 */
public class ResultMsg {

    // ResultMsg 类就两个属性 errorMsg 和 successMsg
    // errorMsgKey 和 successMsg 代表这两个属性的键，用于放入 Map 中
    public static final String errorMsgKey = "errorMsg";

    public static final String successMsgKey = "successMsg";

    private String errorMsg;

    private String successMsg;


    // setter getter
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }


    // 生成 ResultMsg 对象的静态方法
    // 两种对象，正确的结果，错误信息errorMsg为空；错误的结果，正确信息successMsg为空
    // 所以根据 ResultMsg 对象的 errorMsg 属性判断是正确的还是错误的结果
    public static ResultMsg errorMsg(String msg) {
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setErrorMsg(msg);
        return resultMsg;
    }
    public static ResultMsg successMsg(String msg) {
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setSuccessMsg(msg);
        return resultMsg;
    }


    /**
     * 判断是否成功；errorMsg 为 null 就表示注册信息填写合理正确
     * @return
     */
    public boolean isSuccess() {
        return errorMsg == null;
    }


    // 以下方法视为在结果为错(即表单填写的不合适比如说邮件未填，密码太短)
    // 返回结果的一个例子 resultMsg{"successMsg": "", "errorMsg": "Email 有误"}
    private Map<String, String> asMap() {
        Map<String, String> map = Maps.newHashMap();
        map.put(successMsgKey, successMsg);
        map.put(errorMsgKey, errorMsg);
        return map;
    }


    // 将 ResultMsg 返回成 url 的形式
    // 比如一个 ResultMsg 类对象 resultMsg{"successMsg": "", "errorMsg": "Email 有误"}
    public String asUrlParams() {
        Map<String, String> map = asMap();      // 只有两个键值对
        Map<String, String> newMap = Maps.newHashMap();      // 只是将map中的非null value转换成URLEncoder.encode(value)而已，显然只有一个键值对
        map.forEach((k, v) -> {
            if (v != null) {
                try {
                    newMap.put(k, URLEncoder.encode(v, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                }
            }
        });


        return Joiner.on("&")
                .useForNull("")                                  // 创建Joiner对象，使用"&"作为键值对之间的分隔符使用""替代null
                .withKeyValueSeparator("=")                      // 使用"="作为键和值之间的分隔符，创建 MapJoiner 对象
                .join(newMap);                                   // 传递 map参数，生成字符串
    }

}
