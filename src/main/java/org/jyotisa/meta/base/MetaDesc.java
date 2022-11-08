/*
 * Copyright (C) By the Author
 * Author    Yara Krymlov
 * Created   2022-11
 */
package org.jyotisa.meta.base;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-11
 */
public class MetaDesc extends MetaText {
    protected String desc;

    public String desc() {
        return desc;
    }

    public void desc(String desc) {
        this.desc = desc;
    }
}
