package com.banyuan.exercise.io.exer;

import java.io.*;

/**
 * 将aaa.txt
 * 分割为aaa_1.txt和aaa_2.txt
 */
public class Ex4 {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("aaa.txt"));
             BufferedWriter bw1 = new BufferedWriter(new FileWriter("aaa_1.txt"));
             BufferedWriter bw2 = new BufferedWriter(new FileWriter("aaa_2.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            bw1.write(sb.toString(), 0, sb.length() / 2);
            bw2.write(sb.toString(), sb.length() / 2, sb.length() - (sb.length() / 2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
