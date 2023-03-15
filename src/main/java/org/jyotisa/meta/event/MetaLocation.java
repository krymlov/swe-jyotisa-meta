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
public class MetaLocation extends MetaTheme {
    private static final long serialVersionUID = -4601809175474681300L;

    protected String lat;
    protected String lon;

    public String lat() {
        return lat;
    }

    public void lat(String latitude) {
        this.lat = latitude;
    }

    public String lon() {
        return lon;
    }

    public void lon(String longitude) {
        this.lon = longitude;
    }
}
