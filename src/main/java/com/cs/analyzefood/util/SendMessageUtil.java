package com.cs.analyzefood.util;

import com.cs.analyzefood.config.MessageConfig;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SendMessageUtil {

    public static void execute(String checkWord, String phoneNum) {
        String smsContent = "【膳食分析网】您正在尝试找回密码，您的验证码为{1}，请于2分钟内正确输入，如非本人操作，请忽略此短信。";
        String tmpSmsContent = "";
        smsContent = smsContent.replace("{1}", checkWord);   //验证码的生成
        try {
            tmpSmsContent = URLEncoder.encode(smsContent, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = MessageConfig.BASE_URL + "/industrySMS/sendSMS";
        String body = "accountSid=" + MessageConfig.ACCOUNT_SID + "&to=" + phoneNum + "&smsContent=" + tmpSmsContent
                + HttpUtil.createCommonParam();

        String result = HttpUtil.post(url, body);
        System.out.println("result:" + System.lineSeparator() + result);
    }

}
