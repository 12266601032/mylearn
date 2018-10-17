package com.example.algorithm;

public class BitOperatorDemo {

    private static int bitMap = 0;
    private static int bit1   = 0b1;
    private static int bit2   = 0b10;
    private static int bit3   = 0b100;
    private static int bit4   = 0b1000;
    private static int bit5   = 0b10000;
    private static int bit6   = 0b100000;
    private static int bit7   = 0b1000000;
    private static int bit8   = 0b10000000;
    private static int bit9   = 0b100000000;
    private static int bit10  = 0b1000000000;
    private static int bit11  = 0b10000000000;
    private static int bit12  = 0b100000000000;
    private static int bit13  = 0b1000000000000;
    private static int bit14  = 0b10000000000000;
    private static int bit15  = 0b100000000000000;
    private static int bit16  = 0b1000000000000000;
    private static int bit17  = 0b10000000000000000;
    private static int bit18  = 0b100000000000000000;
    private static int bit19  = 0b1000000000000000000;
    private static int bit20  = 0b10000000000000000000;
    private static int bit21  = 0b100000000000000000000;
    private static int bit22  = 0b1000000000000000000000;
    private static int bit23  = 0b10000000000000000000000;
    private static int bit24  = 0b100000000000000000000000;
    private static int bit25  = 0b1000000000000000000000000;
    private static int bit26  = 0b10000000000000000000000000;
    private static int bit27  = 0b100000000000000000000000000;
    private static int bit28  = 0b1000000000000000000000000000;
    private static int bit29  = 0b10000000000000000000000000000;
    private static int bit30  = 0b100000000000000000000000000000;
    private static int bit31  = 0b1000000000000000000000000000000;
    private static int bit32  = 0b10000000000000000000000000000000;

    public static void main(String[] args) {

        bitMap |= bit1 | bit2 | bit3 | bit4 | bit5 | bit6 | bit7 | bit8 | bit9 | bit10 |
                  bit11 | bit12 | bit13 | bit14 | bit15 | bit16 | bit17 | bit18 | bit19 |
                  bit20 |bit21 | bit22 | bit23 | bit24 | bit25 | bit26 | bit27 | bit28 | bit29 |
                  bit30 | bit31 |  bit32;

        System.out.println(Integer.MAX_VALUE);
        System.out.println((1l << 31) - 1);
        System.out.println(0b01111111111111111111111111111111);
        assert (bitMap == -1) && (bitMap == 0b11111111111111111111111111111111);

    }
}
