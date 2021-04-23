package com.banyuan.exercise.io.exer;

import java.io.*;

public class Ex4_2 {
    public static void main(String[] args) {

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream("aaa.txt"));
             BufferedOutputStream bos1 = new BufferedOutputStream(new FileOutputStream("aaa_1.txt"));
             BufferedOutputStream bos2 = new BufferedOutputStream(new FileOutputStream("aaa_2.txt"))
        ) {
            int allBytesNumber = bis.available();
            int restBytes = allBytesNumber / 2;
            byte[] b1 = new byte[8];

            while (restBytes > 0) {
                int len = bis.read(b1, 0, Math.min(restBytes, b1.length));
                restBytes -= len;
                bos1.write(b1, 0, len);
            }
            int len2;
            while ((len2 = bis.read(b1)) != -1) {
                bos2.write(b1, 0, len2);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
