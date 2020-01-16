package com.zlh.common.util;

import com.alibaba.fastjson.JSONException;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


/**
 * @author 10448
 */
public class SMSUtil {

    private static final int appid = 0;

    private static final String appkey = "";

    private static final int templateId = 0;

    private static final String smsSign = "";

    public static String send(String phoneNumbers) {
        int yzm = random();
        String num = Integer.toString(yzm);
        SmsSingleSenderResult result = null;
        try {
            //String[] params ={};
            String sss = yzm + "";
            // 传入参数列表
            ArrayList<String> params = new ArrayList<String>();
            params.add(sss);
            //String smsSign = "" + yzm + "为您的登录验证码，请于2分钟内填写。如非本人操作，请忽略本短信。"; // 这里的签名"腾讯云"只是一个示例，真实的签名需要在短信控制台中申请，另外签名参数使用的是`签名内容`，而不是`签名ID
            //System.out.println(smsSign);
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
//            result = ssender.send(0, "86", phoneNumbers, smsSign, "", "");
            result = ssender.sendWithParam("86", phoneNumbers, templateId, params, smsSign, "", "");
            if (result.result == 0) {
                //发送成功返回验证码
                //返回信息体 {"result":0,"errmsg":"OK","ext":"","sid":"8:mnVwqnAcZFAgpqGILKR20191026","fee":1}
                return num;
            }
        } catch (HTTPException e) {
            //System.out.println("HTTP响应码错误");
            return result.toString();
        } catch (JSONException e) {
            //System.out.println("json解析错误");
            return result.toString();
        } catch (IOException e) {
            //System.out.println(" 网络IO错误");
            return result.toString();
        }
       /* catch (com.github.qcloudsms.httpclient.HTTPException e) {
            e.printStackTrace();
        }*/
        return result.toString();
    }

    /**
     * 生成6位验证码
     */
    public static int random() {
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        int[] c = new int[6];
        for (int i = 0; i < 6; i++) {
            c[i] = r.nextInt(9) + 1;
            sb.append(c[i]);
        }
        return Integer.parseInt(sb.toString());
    }

}