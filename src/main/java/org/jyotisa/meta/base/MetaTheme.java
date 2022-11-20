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
public class MetaTheme extends MetaDesc {
    private static final long serialVersionUID = -1007977171019775813L;

    protected String weight;
    protected String style;
    protected String size;
    protected String color;
    protected String family;

    public String weight() {
        return weight;
    }

    public void weight(String weight) {
        this.weight = weight;
    }

    public String style() {
        return style;
    }

    public void style(String style) {
        this.style = style;
    }

    public String size() {
        return size;
    }

    public void size(String size) {
        this.size = size;
    }

    public String color() {
        return color;
    }

    public void color(String color) {
        this.color = color;
    }

    public String family() {
        return family;
    }

    public void family(String family) {
        this.family = family;
    }
}
