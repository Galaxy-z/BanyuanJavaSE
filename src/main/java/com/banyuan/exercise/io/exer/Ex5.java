package com.banyuan.exercise.io.exer;

import java.io.*;

public class Ex5 {
    public static void main(String[] args) {
        int n = 4;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(new FileInputStream("aaa.txt"));
            int total = bis.available();
            byte[] b = new byte[8];
            int part = total / n;
            for (int i = 0; i < n; i++) {
                int rest;
                if (i != n - 1) {
                    rest = part;
                } else {
                    rest = total - part * (n - 1);
                }
                bos = new BufferedOutputStream(new FileOutputStream("aaa_" + (i + 1) + ".txt"));
                while (rest > 0) {
                    int len = bis.read(b, 0, Math.min(rest, b.length));
                    bos.write(b, 0, len);
                    rest -= len;
                }
                bos.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }
}
