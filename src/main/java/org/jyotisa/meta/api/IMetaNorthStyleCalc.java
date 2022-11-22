package org.jyotisa.meta.api;

public interface IMetaNorthStyleCalc {
    int[][] calc(int bhava);
    int[] calcRasiCords(int rasi);
    int[] calcPlanetBlockCords(int block);
}
