package com.banyuan.exercise.thread.ex.ex3;

import java.io.*;

public class CutFile {
    public static void main(String[] args) {
        int n = 3;
        File fromFile = new File("aaa.txt");
        long total = fromFile.length();
        int avg = (int)total/n;
        for (int i = 0; i < n; i++) {
            File toFile = new File("aaa"+(i+1)+".txt");
            int start;
            int end;
            start = avg * n;
            if (i < n-1){
                end = start + avg -1;
            }else {
                end = (int)total;
            }
            new Thread(()->{
                cut(fromFile,toFile,start,end);
            }).start();
        }

    }

    public static void cut(File fromFile, File toFile, int start, int end) {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fromFile));
             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(toFile))) {
            int rest = end - start +1;
            byte[] b = new byte[8];
            bis.skip(start-1);
            while (rest > 0) {
                int len = bis.read(b, 0, Math.min(rest, b.length));
                if (len!=-1)
                bos.write(b, 0, len);
                rest -= len;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
