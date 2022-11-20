/*
 * Copyright (C) By the Author
 * Author    Yara Krymlov
 * Created   2022-11
 */
package org.jyotisa.meta.options;

import org.jyotisa.meta.api.ViewStyle;
import org.jyotisa.meta.base.MetaCode;

import static org.jyotisa.api.varga.IVarga.D01_CD;
import static org.jyotisa.meta.api.ViewStyle.south;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-11
 */
public class MetaView extends MetaCode {
    private static final long serialVersionUID = -8415643060475155917L;

    protected String view; // D1, D9, SY
    protected ViewStyle style;

    public MetaView() {
        this.view = D01_CD;
        this.style = south;
    }

    public MetaView(ViewStyle style, String view) {
        this.style = style;
        this.view = view;
    }

    public String view() {
        return view;
    }

    public void view(String view) {
        this.view = view;
    }

    public ViewStyle style() {
        return style;
    }

    public void style(ViewStyle style) {
        this.style = style;
    }
}
