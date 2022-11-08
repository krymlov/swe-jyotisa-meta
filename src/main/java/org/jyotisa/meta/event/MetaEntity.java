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
public class MetaEntity extends MetaTheme {
    protected String link;

    public String link() {
        return link;
    }

    public void link(String link) {
        this.link = link;
    }
}
