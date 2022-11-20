/*
 * Copyright (C) By the Author
 * Author    Yara Krymlov
 * Created   2022-11
 */
package org.jyotisa.meta.kundali;

import org.jyotisa.meta.base.MetaStyle;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-11
 */
public class MetaNaksatra extends MetaStyle {
    private static final long serialVersionUID = -7945091701598889746L;
    public static final MetaNaksatra NIL_NAKSATRA = new MetaNaksatra();

    protected Integer lord;

    public Integer lord() {
        return lord;
    }

    public void lord(Integer lord) {
        this.lord = lord;
    }
}
