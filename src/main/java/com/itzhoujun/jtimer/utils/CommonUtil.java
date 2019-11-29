package com.itzhoujun.jtimer.utils;

import org.springframework.util.DigestUtils;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtil {

    public static String encryPwd(String password, String salt) throws NoSuchAlgorithmException {

        String data = password + salt;
        String tmp = DigestUtils.md5DigestAsHex(data.getBytes());
        return DigestUtils.md5DigestAsHex(tmp.getBytes());
    }

    public static String formatDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return  sdf.format(date);
    }

}
