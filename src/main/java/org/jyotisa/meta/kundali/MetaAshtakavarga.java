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
 * Sarvashtakavarga - the combined bindu total of the seven classical grahas in each rasi.
 * <p>
 * The Sarva row is carried with the eight Bhinnashtakavarga rows behind it, so a consumer can
 * render the classical table - one row per point, the total beneath - without a second pass.
 * <p>
 * <b>The total is the seven classical grahas, not the eight points.</b> Lagna has a Bhinna row of
 * its own but contributes nothing to Sarva, so a column adds up only when Lagna is left out.
 * <p>
 * {@code complete} is false when the chart could not supply all 8 contributing points - the usual
 * cause being no ascendant. Every total is then short by whatever the missing points would have
 * given, and must not be compared against a reference chart.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public class MetaAshtakavarga implements IMetaPojo {
    private static final long serialVersionUID = 1174266023295350885L;

    protected Boolean complete;

    protected final List<MetaSarva> sarva = new ArrayList<>(12);
    protected final List<MetaBhinna> bhinna = new ArrayList<>(8);

    public Boolean complete() {
        return complete;
    }

    public void complete(Boolean complete) {
        this.complete = complete;
    }

    public List<MetaSarva> sarva() {
        return sarva;
    }

    public List<MetaBhinna> bhinna() {
        return bhinna;
    }
}
