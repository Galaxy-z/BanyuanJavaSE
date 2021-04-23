package com.banyuan.exercise.io.exer;

import java.io.*;

public class Ex2 {
    public static void main(String[] args) {
        try (BufferedReader br1 = new BufferedReader(new FileReader("aaa.txt"));
             BufferedReader br2 = new BufferedReader(new FileReader("bbb.txt"));
             BufferedWriter bw = new BufferedWriter(new FileWriter("ccc.txt"))) {
            int count = 1;
            String line1;
            String line2;
            while ((line1 = br1.readLine()) != null | (line2 = br2.readLine()) != null) {
                if (line1 != null && count % 2 == 1) {
                    bw.write(line1);
                    bw.newLine();
                    bw.flush();
                }
                if (line2 != null && count % 2 == 0) {
                    bw.write(line2);
                    bw.newLine();
                    bw.flush();
                }
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
