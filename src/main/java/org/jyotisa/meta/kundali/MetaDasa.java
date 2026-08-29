/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.meta.kundali;

import org.jyotisa.meta.base.MetaDesc;

/**
 * One of the nine Vimsottari lords, as a reference entry.
 * <p>
 * Filled the way a bhava is - {@code name} the ordinal, {@code text} the short alias, {@code desc}
 * the full name - with {@code text} taken from the <b>ruling graha</b> rather than from the dasha's
 * own alias list, whose second constant is the code again. So a consumer that already renders
 * grahas by their two-letter code renders a dasha row with what it has.
 * <p>
 * {@code years} is the lord's share of the 120-year cycle. It lives here rather than on every
 * period because it is a property of the lord, and the nine of them sum to 120.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public class MetaDasa extends MetaDesc {
    private static final long serialVersionUID = 4472068917155631207L;
    public static final MetaDasa NIL_DASA = new MetaDasa();

    protected Integer years;

    public Integer years() {
        return years;
    }

    public void years(Integer years) {
        this.years = years;
    }
}
