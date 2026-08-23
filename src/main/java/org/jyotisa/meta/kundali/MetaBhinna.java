/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.meta.kundali;

import org.jyotisa.meta.api.IMetaPojo;

/**
 * One contributing point's Bhinnashtakavarga - its own bindu count in each of the twelve rasis.
 * <p>
 * {@link #bindu()} is indexed by rasi in the same order {@code MetaAshtakavarga.sarva()} lists
 * them, i.e. Mesha first, so the two line up column for column without a lookup.
 * <p>
 * Note the Sarva total is the seven classical grahas only - Lagna has a row here but does not
 * contribute to it, so a column will not add up if Lagna's row is counted in.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public class MetaBhinna implements IMetaPojo {
    private static final long serialVersionUID = 5301826604772134919L;

    protected String graha;
    protected int[] bindu;

    public String graha() {
        return graha;
    }

    public void graha(String graha) {
        this.graha = graha;
    }

    public int[] bindu() {
        return bindu;
    }

    public void bindu(int[] bindu) {
        this.bindu = bindu;
    }
}
