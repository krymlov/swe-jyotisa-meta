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
 * Bhava Chalita - Porphyry cusps read as Sripati bhavas.
 * <p>
 * {@code calculated} is false for a chart built without an ascendant, and then {@link #bhavas()}
 * is empty rather than counted from zero.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 */
public class MetaChalita implements IMetaPojo {
    private static final long serialVersionUID = 6011840055517266407L;

    protected Boolean calculated;

    protected final List<MetaChalitaBhava> bhavas = new ArrayList<>(12);

    public Boolean calculated() {
        return calculated;
    }

    public void calculated(Boolean calculated) {
        this.calculated = calculated;
    }

    public List<MetaChalitaBhava> bhavas() {
        return bhavas;
    }
}
