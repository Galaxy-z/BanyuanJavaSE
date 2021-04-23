package com.banyuan.exercise.io.exer;

import java.io.*;

/**
 * 合并两个文件内容
 */
public class Ex1 {
    public static void main(String[] args) {
        try (BufferedReader br1 = new BufferedReader(new FileReader("aaa.txt"));
             BufferedReader br2 = new BufferedReader(new FileReader("bbb.txt"));
             BufferedWriter bw = new BufferedWriter(new FileWriter("ccc.txt"))) {
            String line;
            while ((line = br1.readLine()) != null) {
                bw.write(line);
                bw.newLine();
            }
            while ((line = br2.readLine()) != null) {
                bw.write(line);
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
