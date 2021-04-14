package com.banyuan.exercise.io;

import java.io.FileInputStream;
import java.io.IOException;

public class Test {
    public static void main(String[] args) {

        FileInputStream fis = null;
        try {
            fis = new FileInputStream("aaa.txt");
            byte[] buf = new byte[10];

            int len = fis.read(buf);
            System.out.println(new String(buf));
            System.out.println(len);
            len = fis.read(buf);
            System.out.println(new String(buf));
            System.out.println(len);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
