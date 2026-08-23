/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.meta.kundali;

import org.jyotisa.meta.api.IMetaPojo;

/**
 * One rasi's Sarvashtakavarga total, with the bhava it falls in.
 * <p>
 * The bhava is carried rather than left to the consumer so the table stands on its own - it is
 * the whole-sign distance from the lagna's rasi, the same convention the chart's own bhava
 * columns use.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public class MetaSarva implements IMetaPojo {
    private static final long serialVersionUID = 8135402210077745529L;

    protected Integer rasi;
    protected Integer bhava;
    protected Integer sarva;

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

    public Integer sarva() {
        return sarva;
    }

    public void sarva(Integer sarva) {
        this.sarva = sarva;
    }
}
