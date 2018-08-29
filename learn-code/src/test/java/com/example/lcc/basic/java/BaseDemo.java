package com.example.lcc.basic.java;

import org.junit.Test;

public class BaseDemo {

    @Test
    public void bitArith() {

        //int i = 0b111_0001; //二进制可以使用_来分隔提高可读性
        //(h ^ (h >>> 16)) & HASH_BITS
        System.out.println(toBinaryString(3) + "^(" + toBinaryString(3) + ">>>16) & " + toBinaryString(15));
        System.out.println(toBinaryString(3) + "^(" + toBinaryString(3>>>16) + ") & " + toBinaryString(15));
        System.out.println(toBinaryString(3^(3>>>16)) + " & " + toBinaryString(15));
        System.out.println(toBinaryString(3^(3>>>16) & 15));

    }


    private String toBinaryString(int i){
        String binaryString = Integer.toBinaryString(i);
        StringBuilder sb = new StringBuilder();
        int len = 32 - binaryString.length();
        while(len-- > 0){
            sb.append("0");
        }
        sb.append(binaryString);

        char[] chars = sb.toString().toCharArray();
        StringBuilder formatted = new StringBuilder();
        for (int j = 0; j < chars.length; j++) {
            formatted.append(chars[j]);
            if(j > 0 && j < 31 && (j + 1) % 4 == 0){
                formatted.append("_");
            }
        }
        return formatted.toString();
    }



}
