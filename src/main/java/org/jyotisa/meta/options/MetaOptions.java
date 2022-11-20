/*
 * Copyright (C) By the Author
 * Author    Yara Krymlov
 * Created   2022-11
 */
package org.jyotisa.meta.options;

import org.jyotisa.meta.api.IMetaPojo;
import org.jyotisa.meta.base.MetaTheme;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yura Krymlov
 * @version 1.0, 2022-11
 */
public class MetaOptions implements IMetaPojo {
    private static final long serialVersionUID = 308967434572171691L;

    protected final List<MetaView> views = new ArrayList<>(2);
    protected final List<MetaTheme> groups = new ArrayList<>(2);
    protected final List<MetaOption> items = new ArrayList<>(24);

    public List<MetaTheme> groups() {
        return groups;
    }

    public List<MetaOption> items() {
        return items;
    }

    public List<MetaView> views() {
        return views;
    }
}
