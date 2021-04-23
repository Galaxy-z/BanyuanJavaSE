package com.banyuan.homework.regex;



import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Exer {
    public static void main(String[] args) {
        String s1 = "13811112222";
        System.out.println(s1.matches("^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$"));

        String s2 = "no4324_njnf";
        System.out.println(s2.matches("^\\w{6,16}$"));

        String s3 = "欢迎使用:aaa@163.com欢迎使用:bb@qq.com欢迎使用:cc_001@sina.com欢迎使用:007@sohu.com欢迎使用:ee001@taobao.com";
        Pattern p  =Pattern.compile("\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+");
        Matcher m = p.matcher(s3);
        while (m.find()){
            System.out.println(m.group(0));
        }
        String s4 = m.replaceAll("bailixi_nj@163.com");
        System.out.println(s4);

    }

}
