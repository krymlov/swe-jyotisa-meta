/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.meta.kundali;

import org.jyotisa.meta.api.IMetaPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * One bhava of the Bhava Chalita table.
 * <p>
 * The three longitudes are each split the way {@code MetaObject} splits a graha's: a rasi to name
 * the sign and a {@code degr} string for the position inside it, so a consumer composes them
 * against the document's own {@code rasi} table and never has to parse a degree.
 * <p>
 * {@code madhya} is the trisection point itself and the <b>middle</b> of the bhava, not its start
 * - the lagna sits halfway along the first one. {@code rasi} belongs to the madhya, which is the
 * sign the bhava is named by.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public class MetaChalitaBhava implements IMetaPojo {
    private static final long serialVersionUID = 3947106622885511921L;

    protected Integer bhava;

    protected Integer rasi;
    protected String madhya;

    protected Integer startRasi;
    protected String start;

    protected Integer closeRasi;
    protected String close;

    /** the codes of the grahas that fall in this bhava, in the chart's own object order */
    protected final List<String> grahas = new ArrayList<>(4);

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

    public String madhya() {
        return madhya;
    }

    public void madhya(String madhya) {
        this.madhya = madhya;
    }

    public Integer startRasi() {
        return startRasi;
    }

    public void startRasi(Integer startRasi) {
        this.startRasi = startRasi;
    }

    public String start() {
        return start;
    }

    public void start(String start) {
        this.start = start;
    }

    public Integer closeRasi() {
        return closeRasi;
    }

    public void closeRasi(Integer closeRasi) {
        this.closeRasi = closeRasi;
    }

    public String close() {
        return close;
    }

    public void close(String close) {
        this.close = close;
    }

    public List<String> grahas() {
        return grahas;
    }
}
