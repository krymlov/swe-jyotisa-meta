/*
 * Copyright (C) By the Author
 * Author    Yara Krymlov
 * Created   2022-11
 */
package org.jyotisa.meta.kundali;

import org.jyotisa.meta.api.IMetaPojo;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-11
 */
public class MetaBhavaSeq implements IMetaPojo {
    private static final long serialVersionUID = 6220173863604847340L;

    protected Integer bhava;
    protected Integer rasi;

    protected int[][] bhavaShape;
    protected int[] rasiShape;
    protected int[] grahaShape;

    public Integer bhava() {
        return bhava;
    }

    public void bhava(Integer bhava) {
        this.bhava = bhava;
    }

    public Integer rasi() {
        return rasi;
    }

    public void rasi(Integer rasi) {
        this.rasi = rasi;
    }

    public int[][] bhavaShape() {
        return bhavaShape;
    }

    public void bhavaShape(int[][] bhavaShape) {
        this.bhavaShape = bhavaShape;
    }

    public int[] rasiShape() {
        return rasiShape;
    }

    public void rasiShape(int[] rasiShape) {
        this.rasiShape = rasiShape;
    }

    public int[] grahaShape() {
        return grahaShape;
    }

    public void grahaShape(int[] grahaShape) {
        this.grahaShape = grahaShape;
    }
}
