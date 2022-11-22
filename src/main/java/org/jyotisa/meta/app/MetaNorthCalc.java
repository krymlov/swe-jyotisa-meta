/*
 * Copyright (C) By the Author
 * Author    Danylo Krymlov
 * Created   2022-11
 */
package org.jyotisa.meta.app;

/**
 * @author Danylo Krymlov
 * @version 1.0, 2022-11
 */
public class MetaNorthCalc {
    private static final int startX = 0;
    private static final int startY = 0;

    private final int width;
    private final int height;

    public MetaNorthCalc(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int[][] calc(final int bhava) {
        switch (bhava) {
            case 1:
                return bhava1();
            case 2:
                return bhava2();
            case 3:
                return bhava3();
            case 4:
                return bhava4();
            case 5:
                return bhava5();
            case 6:
                return bhava6();
            case 7:
                return bhava7();
            case 8:
                return bhava8();
            case 9:
                return bhava9();
            case 10:
                return bhava10();
            case 11:
                return bhava11();
            case 12:
                return bhava12();
        }
        throw new IllegalArgumentException("bhava");
    }

    public int[] calcRasiCords(final int rasi) {
        switch (rasi) {
            case 1:
                return rasinum1();
            case 2:
                return rasinum2();
            case 3:
                return rasinum3();
            case 4:
                return rasinum4();
            case 5:
                return rasinum5();
            case 6:
                return rasinum6();
            case 7:
                return rasinum7();
            case 8:
                return rasinum8();
            case 9:
                return rasinum9();
            case 10:
                return rasinum10();
            case 11:
                return rasinum11();
            case 12:
                return rasinum12();
        }

        throw new IllegalArgumentException("rasi");
    }

    public int[] calcPlanetBlockCords(final int block) {
        switch (block) {
            case 1:
                return planetBlock1();
            case 2:
                return planetBlock2();
            case 3:
                return planetBlock3();
            case 4:
                return planetBlock4();
            case 5:
                return planetBlock5();
            case 6:
                return planetBlock6();
            case 7:
                return planetBlock7();
            case 8:
                return planetBlock8();
            case 9:
                return planetBlock9();
            case 10:
                return planetBlock10();
            case 11:
                return planetBlock11();
            case 12:
                return planetBlock12();
        }

        throw new IllegalArgumentException("block");
    }

    protected int[][] bhava1() {
        final int[][] cords = new int[4][4];
        //1st line
        cords[0][0] = width / 2;
        cords[0][1] = width / 2 + width / 4;
        cords[0][2] = startY;
        cords[0][3] = height / 4;
        //2nd line
        cords[1][0] = cords[0][1];
        cords[1][1] = width / 2;
        cords[1][2] = cords[0][3];
        cords[1][3] = height / 2;
        //3rd
        cords[2][0] = cords[1][1];
        cords[2][1] = width / 4;
        cords[2][2] = cords[1][3];
        cords[2][3] = height / 4;
        //4th
        cords[3][0] = cords[2][1];
        cords[3][1] = cords[0][0];
        cords[3][2] = cords[2][3];
        cords[3][3] = cords[0][2];
        return cords;
    }

    protected int[][] bhava2() {
        final int[][] cords = new int[2][4];
        //1st line
        cords[0][0] = startX;
        cords[0][1] = width / 4;
        cords[0][2] = startY;
        cords[0][3] = height / 4;
        //2nd line
        cords[1][0] = cords[0][1];
        cords[1][1] = width / 2;
        cords[1][2] = cords[0][3];
        cords[1][3] = cords[0][2];
        return cords;
    }

    protected int[][] bhava3() {
        final int[][] cords = new int[2][4];
        //1st line
        cords[0][0] = startX;
        cords[0][1] = width / 4;
        cords[0][2] = startY;
        cords[0][3] = height / 4;
        //2nd line
        cords[1][0] = cords[0][1];
        cords[1][1] = cords[0][0];
        cords[1][2] = cords[0][3];
        cords[1][3] = height / 2;
        return cords;
    }

    protected int[][] bhava4() {
        final int[][] cords = new int[4][4];
        //1st line
        cords[0][0] = width / 4;
        cords[0][1] = width / 2;
        cords[0][2] = height / 4;
        cords[0][3] = height / 2;
        //2nd line
        cords[1][0] = cords[0][1];
        cords[1][1] = width / 4;
        cords[1][2] = cords[0][3];
        cords[1][3] = height / 2 + height / 4;
        //3rd
        cords[2][0] = cords[1][1];
        cords[2][1] = startX;
        cords[2][2] = cords[1][3];
        cords[2][3] = height / 2;
        //4th
        cords[3][0] = cords[2][1];
        cords[3][1] = cords[0][0];
        cords[3][2] = cords[2][3];
        cords[3][3] = cords[0][2];
        return cords;
    }

    protected int[][] bhava5() {
        final int[][] cords = new int[2][4];
        //1st line
        cords[0][0] = startX;
        cords[0][1] = width / 4;
        cords[0][2] = height / 2;
        cords[0][3] = height / 2 + height / 4;
        //2nd line
        cords[1][0] = cords[0][1];
        cords[1][1] = cords[0][0];
        cords[1][2] = cords[0][3];
        cords[1][3] = height;
        return cords;
    }

    protected int[][] bhava6() {
        final int[][] cords = new int[2][4];
        //1st line
        cords[0][0] = startX;
        cords[0][1] = width / 4;
        cords[0][2] = height;
        cords[0][3] = height / 2 + height / 4;
        //2nd line
        cords[1][0] = cords[0][1];
        cords[1][1] = width / 2;
        cords[1][2] = cords[0][3];
        cords[1][3] = cords[0][2];
        return cords;
    }

    protected int[][] bhava7() {
        final int[][] cords = new int[4][4];
        //1st line
        cords[0][0] = width / 4;
        cords[0][1] = width / 2;
        cords[0][2] = height / 2 + height / 4;
        cords[0][3] = height / 2;
        //2nd line
        cords[1][0] = cords[0][1];
        cords[1][1] = width / 2 + width / 4;
        cords[1][2] = cords[0][3];
        cords[1][3] = height / 2 + height / 4;
        //3rd
        cords[2][0] = cords[1][1];
        cords[2][1] = width / 2;
        cords[2][2] = cords[1][3];
        cords[2][3] = height;
        //4th
        cords[3][0] = cords[2][1];
        cords[3][1] = cords[0][0];
        cords[3][2] = cords[2][3];
        cords[3][3] = cords[0][2];
        return cords;
    }

    protected int[][] bhava8() {
        final int[][] cords = new int[2][4];
        //1st line
        cords[0][0] = width / 2;
        cords[0][1] = width / 2 + width / 4;
        cords[0][2] = height;
        cords[0][3] = height / 2 + height / 4;
        //2nd line
        cords[1][0] = cords[0][1];
        cords[1][1] = width;
        cords[1][2] = cords[0][3];
        cords[1][3] = height;
        return cords;
    }

    protected int[][] bhava9() {
        final int[][] cords = new int[2][4];
        //1st line
        cords[0][0] = width;
        cords[0][1] = width / 2 + width / 4;
        cords[0][2] = height;
        cords[0][3] = height / 2 + height / 4;
        //2nd line
        cords[1][0] = cords[0][1];
        cords[1][1] = width;
        cords[1][2] = cords[0][3];
        cords[1][3] = height / 2;
        return cords;
    }

    protected int[][] bhava10() {
        final int[][] cords = new int[4][4];
        //1st line
        cords[0][0] = width / 2;
        cords[0][1] = width / 2 + width / 4;
        cords[0][2] = height / 2;
        cords[0][3] = height / 4;
        //2nd line
        cords[1][0] = cords[0][1];
        cords[1][1] = width;
        cords[1][2] = cords[0][3];
        cords[1][3] = height / 2;
        //3rd
        cords[2][0] = cords[1][1];
        cords[2][1] = width / 2 + width / 4;
        cords[2][2] = cords[1][3];
        cords[2][3] = height / 2 + height / 4;
        //4th
        cords[3][0] = cords[2][1];
        cords[3][1] = cords[0][0];
        cords[3][2] = cords[2][3];
        cords[3][3] = cords[0][2];
        return cords;
    }

    protected int[][] bhava11() {
        final int[][] cords = new int[2][4];
        //1st line
        cords[0][0] = width;
        cords[0][1] = width / 2 + width / 4;
        cords[0][2] = height / 2;
        cords[0][3] = height / 4;
        //2nd line
        cords[1][0] = cords[0][1];
        cords[1][1] = cords[0][0];
        cords[1][2] = cords[0][3];
        cords[1][3] = startY;
        return cords;
    }

    protected int[][] bhava12() {
        final int[][] cords = new int[2][4];
        //1st line
        cords[0][0] = width;
        cords[0][1] = width / 2 + width / 4;
        cords[0][2] = startY;
        cords[0][3] = height / 4;
        //2nd line
        cords[1][0] = cords[0][1];
        cords[1][1] = width / 2;
        cords[1][2] = cords[0][3];
        cords[1][3] = cords[0][2];
        return cords;
    }

    //x y
    protected int[] rasinum1() {
        final int[] cords = new int[2];
        cords[0] = (width / 2) - (width / 40);
        cords[1] = (height / 2) - ((height / 40) * 3);
        return cords;
    }

    protected int[] rasinum2() {
        final int[] cords = new int[2];
        cords[0] = (width / 4) - (width / 40);
        cords[1] = (height / 4) - ((height / 40) * 3);
        return cords;
    }

    protected int[] rasinum3() {
        final int[] cords = new int[2];
        cords[0] = (width / 4) - ((width / 40) * 3);
        cords[1] = (height / 4) - (height / 40);
        return cords;
    }

    protected int[] rasinum4() {
        final int[] cords = new int[2];
        cords[0] = (width / 2) - ((width / 40) * 3);
        cords[1] = (height / 2) - (height / 40);
        return cords;
    }

    protected int[] rasinum5() {
        final int[] cords = new int[2];
        cords[0] = (width / 4) - ((width / 40) * 3);
        cords[1] = height / 2 + (height / 4 - height / 40);
        return cords;
    }

    protected int[] rasinum6() {
        final int[] cords = new int[2];
        cords[0] = (width / 4) - (width / 40);
        cords[1] = height / 2 + (height / 4 + height / 40);
        return cords;
    }

    protected int[] rasinum7() {
        final int[] cords = new int[2];
        cords[0] = width / 2 - width / 40;
        cords[1] = height / 2 + height / 40;
        return cords;
    }

    protected int[] rasinum8() {
        final int[] cords = new int[2];
        cords[0] = width / 2 + (width / 4 - width / 40);
        cords[1] = height / 2 + height / 4 + height / 40;
        return cords;
    }

    protected int[] rasinum9() {
        final int[] cords = new int[2];
        cords[0] = width / 2 + width / 4 + width / 40;
        cords[1] = height / 2 + (height / 4 - height / 40);
        return cords;
    }

    protected int[] rasinum10() {
        final int[] cords = new int[2];
        cords[0] = width / 2 + width / 40;
        cords[1] = height / 2 - height / 40;
        return cords;
    }

    protected int[] rasinum11() {
        final int[] cords = new int[2];
        cords[0] = width / 2 + width / 4 + width / 40;
        cords[1] = height / 4 - height / 40;
        return cords;
    }

    protected int[] rasinum12() {
        final int[] cords = new int[2];
        cords[0] = width / 2 + width / 4 - width / 40;
        cords[1] = height / 4 - ((height / 40) * 3);
        return cords;
    }

    //x y width height
    protected int[] planetBlock1() {
        final int[] cords = new int[4];
        cords[0] = width / 4 + (width / 40 * 3);
        cords[1] = height / 10 + height / 20;
        cords[2] = width / 4 + width / 10;
        cords[3] = height / 5;
        return cords;
    }

    protected int[] planetBlock2() {
        final int[] cords = new int[4];
        cords[0] = width / 20;
        cords[1] = startY;
        cords[2] = width / 4 + width / 10 + width / 20;
        cords[3] = height / 10 + height / 20;
        return cords;
    }

    protected int[] planetBlock3() {
        final int[] cords = new int[4];
        cords[0] = startX;
        cords[1] = height / 20 + height / 40 + height / 80;
        cords[2] = width / 5;
        cords[3] = height / 4 + height / 20 + height / 40;
        return cords;
    }

    protected int[] planetBlock4() {
        final int[] cords = new int[4];
        cords[0] = width / 20 + width / 40;
        cords[1] = height / 4 + height / 10 + height / 20;
        cords[2] = width / 4 + width / 10;
        cords[3] = height / 5;
        return cords;
    }

    protected int[] planetBlock5() {
        final int[] cords = new int[4];
        cords[0] = startX;
        cords[1] = height / 2 + height / 20 + height / 40 + height / 80;
        cords[2] = width / 5;
        cords[3] = height / 4 + height / 20 + height / 40;
        return cords;
    }

    protected int[] planetBlock6() {
        final int[] cords = new int[4];
        cords[0] = width / 20;
        cords[1] = height - height / 4 + height / 10;
        cords[2] = width / 4 + (width / 20 * 3);
        cords[3] = width / 20 * 3;
        return cords;
    }

    protected int[] planetBlock7() {
        final int[] cords = new int[4];
        cords[0] = width / 4 + width / 20 + width / 40;
        cords[1] = height / 2 + (height / 20 * 3);
        cords[2] = width / 4 + width / 10;
        cords[3] = height / 5;
        return cords;
    }

    protected int[] planetBlock8() {
        final int[] cords = new int[4];
        cords[0] = width / 2 + width / 20;
        cords[1] = height - height / 4 + height / 10;
        cords[2] = width / 4 + (width / 20 * 3);
        cords[3] = height / 20 * 3;
        return cords;
    }

    protected int[] planetBlock9() {
        final int[] cords = new int[4];
        cords[0] = width - width / 4 + width / 20;
        cords[1] = height / 2 + height / 20 + height / 40 + height / 80;
        cords[2] = width / 5;
        cords[3] = height / 4 + height / 20 + height / 40;
        return cords;
    }

    protected int[] planetBlock10() {
        final int[] cords = new int[4];
        cords[0] = width / 2 + width / 20 + width / 40;
        cords[1] = height / 4 + height / 10 + height / 20;
        cords[2] = width / 4 + width / 10;
        cords[3] = height / 5;
        return cords;
    }

    protected int[] planetBlock11() {
        final int[] cords = new int[4];
        cords[0] = width - width / 4 + width / 20;
        cords[1] = height / 20 + height / 40 + height / 80;
        cords[2] = width / 5;
        cords[3] = height / 4 + height / 20 + height / 40;
        return cords;
    }

    protected int[] planetBlock12() {
        final int[] cords = new int[4];
        cords[0] = width / 2 + width / 20;
        cords[1] = startY;
        cords[2] = width / 4 + width / 10 + width / 20;
        cords[3] = height / 10 + height / 20;
        return cords;
    }
}
