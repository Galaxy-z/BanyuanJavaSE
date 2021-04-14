package com.banyuan.exercise.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TestFileInputStream {
    public static void main(String[] args) {
        try (FileInputStream fis = new FileInputStream("aaa.txt")) {

            byte[] buf = new byte[10];

            int len = fis.read(buf);
            System.out.println(new String(buf));
            System.out.println(len);
            len = fis.read(buf);
            System.out.println(new String(buf));
            System.out.println(len);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
