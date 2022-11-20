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
public class MetaGroup extends MetaStyle {
    private static final long serialVersionUID = 2432795365001929425L;

    protected String group;

    public String group() {
        return group;
    }

    public void group(String group) {
        this.group = group;
    }
}
