/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.meta.kundali;

import org.jyotisa.meta.base.MetaDesc;

/**
 * One limb of the panchanga - a vaara, tithi, naksatra, nitya yoga or karana.
 * <p>
 * Shaped like {@code MetaRasi} and the other reference entries rather than inventing a second form
 * for the same idea: {@code fid} and {@code code} from the registry, {@code name} the short alias
 * ({@code Syvr}, {@code Ttl}), {@code text} the full one ({@code Surya}, {@code Taitula}), and
 * {@code desc} left for the host to fill as it does elsewhere.
 * <p>
 * {@code progress} is the one thing a reference entry does not have - how far through the limb this
 * chart stands, as a percentage. It is the figure {@code Kundali.toString()} prints in brackets,
 * and what makes the table useful rather than decorative: a tithi at 99% is about to turn. A vaara
 * has none; it is a whole day.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public class MetaLimb extends MetaDesc {
    private static final long serialVersionUID = 1123479005584426851L;

    protected Float progress;

    public Float progress() {
        return progress;
    }

    public void progress(Float progress) {
        this.progress = progress;
    }
}
