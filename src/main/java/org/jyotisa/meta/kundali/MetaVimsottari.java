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
 * The Vimsottari dasha of a chart, to whatever depth was asked for.
 *
 * <h2>How to read a period's end</h2>
 * The tree carries starting moments only, because the periods of any level tile their parent
 * exactly and in order. So for the period at index {@code i} of a list:
 * <pre>
 *   to = (i + 1 &lt; list.size()) ? list.get(i + 1).from()   // the next sibling begins
 *                              : parent's own to           // or the parent ends
 * </pre>
 * and the outermost parent's end is {@link #to()}. That is one rule for the whole tree and it holds
 * at every level, which is why the alternative - a {@code to} on all 9 + 81 + 729 periods - was not
 * worth its size.
 *
 * <h2>What {@code year} means, and why it is here</h2>
 * The name of the rule the periods were measured by. The default, {@code TRUE_SIDEREAL_YEAR}, is
 * not a length at all: a period ends when the Sun has actually travelled {@code years * 360}
 * degrees of sidereal longitude, which is what Jagannatha Hora computes and why period lengths of
 * the same nominal size differ by up to a couple of days. A consumer must therefore not re-derive
 * any boundary from a day count - the dates given are the answer.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public class MetaVimsottari implements IMetaPojo {
    private static final long serialVersionUID = 8079460129015607622L;

    protected Integer levels;
    protected String year;
    protected String to;

    protected final List<MetaPeriod> periods = new ArrayList<>(9);

    /** how deep this was computed: 1 mahadasha, 2 antardasha, 3 pratyantardasha, ... */
    public Integer levels() {
        return levels;
    }

    public void levels(Integer levels) {
        this.levels = levels;
    }

    /** the rule the periods were measured by - see the class note */
    public String year() {
        return year;
    }

    public void year(String year) {
        this.year = year;
    }

    /** the only closing moment in the tree: where the ninth mahadasha ends */
    public String to() {
        return to;
    }

    public void to(String to) {
        this.to = to;
    }

    /** the nine mahadashas, in order, starting with the one running at birth */
    public List<MetaPeriod> periods() {
        return periods;
    }
}
