/*
 * Copyright (C) By the Author
 * Author    Yara Krymlov
 * Created   2022-11
 */
package org.jyotisa.meta.event;

import org.jyotisa.meta.base.MetaTheme;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-11
 */
public class MetaDateTime extends MetaTheme {
    private static final long serialVersionUID = 1966754250274679445L;

    protected String date;
    protected String time;
    protected String zone;

    public String date() {
        return date;
    }

    public void date(String date) {
        this.date = date;
    }

    public String time() {
        return time;
    }

    public void time(String time) {
        this.time = time;
    }

    public String zone() {
        return zone;
    }

    public void zone(String zone) {
        this.zone = zone;
    }
}
