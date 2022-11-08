/*
 * Copyright (C) By the Author
 * Author    Yara Krymlov
 * Created   2022-11
 */
package org.jyotisa.meta.kundali;

import org.jyotisa.meta.api.IMetaJyotisaPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-11
 */
public class MetaRasiSeq implements IMetaJyotisaPojo {
    protected Integer rasi;
    protected Integer bhava;

    protected final List<Integer> shape = new ArrayList<>(4);

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
}
