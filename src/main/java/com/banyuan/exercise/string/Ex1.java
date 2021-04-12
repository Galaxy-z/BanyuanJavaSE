package com.banyuan.exercise.string;

import java.util.Arrays;

public class Ex1 {
    public static void main(String[] args) {
        //1.打印每个ascii码
        String s = "fnaog.agopregj";
        char[] charArray = s.toCharArray();
        for (char c : charArray) {
            System.out.print((int) c + " ");
        }

        //2."abcdefgwwwxyz"里的"www"挖出来
        String s1 = "abcdefgwwwxyz";
        String[] sArr = s1.split("www");
        String s2 = sArr[0] + sArr[1];
        System.out.println(s2);

        //3."ytuwaxyzbd"升序排列，生成新的字符串
        String s3 = "ytuwaxyzbd";
        char[] charArray1 = s3.toCharArray();
        Arrays.sort(charArray1);
        String s4 = new String(charArray1);
        System.out.println(s4);
    }
}
