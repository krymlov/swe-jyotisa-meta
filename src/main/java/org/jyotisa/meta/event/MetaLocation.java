/*
 * Copyright (C) By the Author
 * Author    Yara Krymlov
 * Created   2022-11
 */
package org.jyotisa.meta.event;

import org.jyotisa.meta.base.MetaStyle;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-11
 */
public class MetaLocation extends MetaStyle {
    private static final long serialVersionUID = -3965816142560214082L;

    protected String lttd;
    protected String lgtd;

    public String lttd() {
        return lttd;
    }

    public void lttd(String latitude) {
        this.lttd = latitude;
    }

    public String lgtd() {
        return lgtd;
    }

    public void lgtd(String longitude) {
        this.lgtd = longitude;
    }
}
