/*
 * Copyright (C) By the Author
 * Author    Yara Krymlov
 * Created   2022-11
 */
package org.jyotisa.meta.kundali;

import org.jyotisa.meta.api.IMetaJyotisaPojo;

import java.util.List;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-11
 */
public class MetaRasiSeq implements IMetaJyotisaPojo {
    private static final long serialVersionUID = -5925589435747875223L;

    protected Integer rasi;
    protected Integer bhava;
    protected List<Integer> shape;

    public Integer rasi() {
        return rasi;
    }

    public void rasi(Integer rasi) {
        this.rasi = rasi;
    }

    public Integer bhava() {
        return bhava;
    }

    public void bhava(Integer bhava) {
        this.bhava = bhava;
    }

    public List<Integer> shape() {
        return shape;
    }

    public void shape(List<Integer> shape) {
        this.shape = shape;
    }
}
