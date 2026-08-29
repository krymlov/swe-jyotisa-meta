/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.meta.kundali;

import org.jyotisa.meta.api.IMetaPojo;

import java.util.List;

/**
 * One dasha period at any level - a mahadasha, an antardasha, a pratyantardasha, and so on down.
 * <p>
 * Deliberately three fields and no more, because the count is nine to the power of the depth: nine
 * periods, then 81, 729, 6561. Everything derivable is derived.
 *
 * <h2>There is no {@code to}</h2>
 * Periods tile their parent exactly and in order, so a period ends where the next one begins, and
 * the last one ends where its parent does. The only closing moment in the whole tree is
 * {@link MetaVimsottari#to()}. See that class for the rule written out.
 *
 * <h2>{@code fid} rather than a code</h2>
 * The lord is the index into the document's own {@code dasa} table, exactly as a graha's rasi is an
 * index into its {@code rasi} table. One integer per period rather than a four-character string.
 *
 * <h2>{@code periods} is absent, not empty, on a leaf</h2>
 * The one place this library lets a collection be null. At the deepest level requested there are as
 * many leaves as the whole tree has periods, and an empty list each would be most of the document.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public class MetaPeriod implements IMetaPojo {
    private static final long serialVersionUID = 1266318064290437023L;

    protected Integer fid;
    protected String from;
    protected List<MetaPeriod> periods;

    /** the ruling dasha lord, as an index into the document's {@code dasa} table */
    public Integer fid() {
        return fid;
    }

    public void fid(Integer fid) {
        this.fid = fid;
    }

    /** when this period begins, local to the chart's own place */
    public String from() {
        return from;
    }

    public void from(String from) {
        this.from = from;
    }

    /** the nine sub-periods, or null at the deepest level computed */
    public List<MetaPeriod> periods() {
        return periods;
    }

    public void periods(List<MetaPeriod> periods) {
        this.periods = periods;
    }
}
