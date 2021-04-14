package com.banyuan.exercise.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyFile {
    public static void main(String[] args) {

        try (FileInputStream fis = new FileInputStream("aaa.txt");
             FileOutputStream fos = new FileOutputStream("aaaCopy.txt")) {

            byte[] buf = new byte[10];
            int len;
            while ((len = fis.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
