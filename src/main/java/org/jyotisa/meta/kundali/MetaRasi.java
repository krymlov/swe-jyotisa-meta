/*
 * Copyright (C) By the Author
 * Author    Yara Krymlov
 * Created   2022-11
 */
package org.jyotisa.meta.kundali;

import org.jyotisa.meta.base.MetaTheme;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-11
 */
public class MetaRasi extends MetaTheme {
    private static final long serialVersionUID = 5304229582983852084L;
    public static final MetaRasi NIL_RASI = new MetaRasi();

    protected Integer lord;
    protected Double start;
    protected Double close;

    public Integer lord() {
        return lord;
    }

    public void lord(Integer lord) {
        this.lord = lord;
    }

    public Double start() {
        return start;
    }

    public void start(Double start) {
        this.start = start;
    }

    public Double close() {
        return close;
    }

    public void close(Double close) {
        this.close = close;
    }
}
