package com.banyuan.exercise.io.exer;

import java.io.File;

public class PrintFiles {
    public static void main(String[] args) {
        File file = new File("/Users/edz/Documents/Workspace/liukailai-javase/src");
        printFile(file, 0);

    }

    public static void printFile(File file, int level) {
        File[] files = file.listFiles();
        for (File f : files) {
            for (int i = 0; i < level; i++) {
                System.out.print("\t");
            }

            if (f.isFile()) {
                System.out.println(f.getName());
            }

            if (f.isDirectory()) {
                System.out.print("*");
                System.out.println(f.getName());
                printFile(f, level + 1);
            }

        }

    }

}
