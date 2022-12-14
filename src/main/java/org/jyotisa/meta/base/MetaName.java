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
public class MetaName extends MetaCode {
    private static final long serialVersionUID = 3623173551884813960L;

    protected String name;

    public String name() {
        return name;
    }

    public void name(String name) {
        this.name = name;
    }
}
