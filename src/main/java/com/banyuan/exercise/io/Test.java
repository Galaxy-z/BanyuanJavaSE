package com.banyuan.exercise.io;

import java.io.*;

public class Test {
    public static void main(String[] args) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("aaa.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        try (BufferedWriter bw = new BufferedWriter(new FileWriter("bbb.txt"))){
//            bw.write("1231322");
//            bw.newLine();
//            bw.write("asdsadadsaasfasf");
//            bw.flush();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}
