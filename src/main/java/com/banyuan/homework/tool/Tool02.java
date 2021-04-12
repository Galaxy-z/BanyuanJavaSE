package com.banyuan.homework.tool;

import java.math.BigDecimal;

public class Tool02 {
    public static void main(String[] args) {
        double[] arr = {0.1, 0.2, 0.3, 0.6, 0.5};
        BigDecimal sum = new BigDecimal("0.0");
        BigDecimal product = new BigDecimal("1.0");
        for (double v : arr) {
            sum = sum.add(new BigDecimal(v + ""));
            product = product.multiply(new BigDecimal(v + ""));
        }
        System.out.println("和:" + sum);
        System.out.println("积:" + product);
    }
}
