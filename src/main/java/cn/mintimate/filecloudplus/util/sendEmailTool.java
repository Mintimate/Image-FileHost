package cn.mintimate.filecloudplus.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.Random;

@Component
public class sendEmailTool {
    @Autowired
    JavaMailSenderImpl mailSender;

    public String sendVerifyEmail(String userMail){
        if(!isEmailAddress(userMail)){
            // 判断是否邮箱合法
            return null;
        }
        String emailServiceCode;
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        emailServiceCode = str.toString();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userMail);
        message.setSubject("[Mintimate-ImageFileHost] Please verify your Email");
        message.setText("您好\n\n您尝试在Mintimate-ImageFileHost项目内注册\n\n注册验证码是：" + emailServiceCode+
                "\n\n如果非您操作，可能是您的电子邮箱找到泄露，请忽略该条消息。\n\n" +
                "本项目开源地址:https://github.com/Mintimate/Image-FileHost\n\n"+
                "欢迎访问博客获取更多实用教程：\n"+
                "Mintimate's Blog\nhttps://www.mintimate.cn");
        message.setFrom("noreply@mintimate.cn");
        mailSender.send(message);
        return emailServiceCode;
    }
    public static boolean isEmailAddress(String address) {
        // 是否合法
        boolean flag = false;
        if (StringUtils.isEmpty(address)) {
            return false;
        }
        try {
            String[] addressArr = address.split(",");
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = null;
            for (String str : addressArr) {
                matcher = regex.matcher(str);
                flag = matcher.matches();
                if (!flag) {
                    return false;
                }
            }
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
}
