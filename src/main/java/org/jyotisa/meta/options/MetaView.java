/*
 * Copyright (C) By the Author
 * Author    Yara Krymlov
 * Created   2022-11
 */
package org.jyotisa.meta.options;

import org.jyotisa.meta.api.MetaViewStyle;
import org.jyotisa.meta.base.MetaCode;

import static org.jyotisa.api.varga.IVarga.D01_CD;
import static org.jyotisa.meta.api.MetaViewStyle.south;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-11
 */
public class MetaView extends MetaCode {
    protected String view; // D1, D9, SY
    protected MetaViewStyle style;

    public MetaView() {
        this.view = D01_CD;
        this.style = south;
    }

    public MetaView(MetaViewStyle style, String view) {
        this.style = style;
        this.view = view;
    }

    public String view() {
        return view;
    }

    public void view(String view) {
        this.view = view;
    }

    public MetaViewStyle style() {
        return style;
    }

    public void style(MetaViewStyle style) {
        this.style = style;
    }
}
