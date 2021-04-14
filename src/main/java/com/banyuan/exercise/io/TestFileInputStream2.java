package com.banyuan.exercise.io;

import java.io.FileInputStream;
import java.io.IOException;

public class TestFileInputStream2 {
    public static void main(String[] args) {
        try (FileInputStream fis = new FileInputStream("aaa.txt")) {

            byte[] buf = new byte[10];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len = fis.read(buf)) != -1) {
                sb.append(new String(buf, 0, len));
            }
            System.out.println(sb);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
