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
public class MetaText extends MetaName {
    protected String text;

    public String text() {
        return text;
    }

    public void text(String text) {
        this.text = text;
    }
}
